package com.dorandoran.backend.Post.Model;

import com.dorandoran.backend.File.DTO.Filedto;
import com.dorandoran.backend.File.Model.File;
import com.dorandoran.backend.File.Model.FileRepository;
import com.dorandoran.backend.File.Model.FileService;
import com.dorandoran.backend.File.Model.S3ImageService;
import com.dorandoran.backend.Member.domain.Member;
import com.dorandoran.backend.Member.domain.MemberRepository;
import com.dorandoran.backend.Member.exception.MemberNotFoundException;
import com.dorandoran.backend.Post.dto.*;
import com.dorandoran.backend.Post.dto.check.PostCheckDTO;
import com.dorandoran.backend.Post.dto.summary.PostSummaryDTO;
import com.dorandoran.backend.Post.dto.summary.PostSummaryResponseDTO;
import com.dorandoran.backend.Post.dto.update.PostUpdateDTO;
import com.dorandoran.backend.Post.dto.update.PostUpdateResponseDTO;
import com.dorandoran.backend.Post.exception.PostNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostCommandService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final S3ImageService s3ImageService;
    private final FileRepository fileRepository;
    private final FileService fileService;

    /**
     * 게시물 저장
     */
    @Transactional
    public Long savePost(PostRequestDTO postRequestDTO) {
        Member findMember = memberRepository.findById(postRequestDTO.getMember_id())
                .orElseThrow(() -> new MemberNotFoundException("멤버가 존재하지 않습니다."));

        //파일 유효성 검사
        fileService.validateImage(postRequestDTO.getFile());

        // 파일 엔티티 생성
        File fileEntity = createFileEntity(postRequestDTO.getFile());

        //게시물 엔티티 생성
        Post post = dtoToEntity(postRequestDTO, findMember);
        post.addFile(fileEntity);

        //파일과 게시물 저장
        fileRepository.save(fileEntity); //파일 먼저 저 (ID 필요시)
        Post savePost = postRepository.save(post); //게시물 저장

        return savePost.getId();
    }

    /**
     * 글 상세 조회(단일) - PostCheck, PostCheckResponseDTO 사용
     */
    public PostCheckDTO findPostOne(Long post_id) {
        Post findPost = postRepository.findById(post_id)
                .orElseThrow(() -> new PostNotFoundException());
        return convertToPostCheckDTO(findPost);
    }

    /**
     * 글 목록 조회 - PostSummaryDTO, PostSummaryResponseDTO 사용
     */
    public PostSummaryResponseDTO getPosts(int page, int pageSize) {
        PageRequest pageable = PageRequest.of(page, pageSize);
        Page<Post> postPage = postRepository.findAll(pageable);
        List<PostSummaryDTO> posts = postPage.getContent().stream()
                .map(post -> new PostSummaryDTO(post.getId(), post.getTitle(), post.getCreated_at(),
                        post.getMember() != null ? post.getMember().getId() : null)) // null 체크 포함
                .toList();

        return new PostSummaryResponseDTO(page, pageSize, postPage.getTotalElements(), postPage.getTotalPages(), posts);
    }

    /**
     * 게시물 수정
     */
    @Transactional
    public PostUpdateResponseDTO updatePost(Long postId, PostUpdateDTO postUpdateDTO) {

        validateUpdate(postUpdateDTO);

        Post findPost = postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException());

        findPost.update(postUpdateDTO.getTitle(), postUpdateDTO.getContent());

        // 파일 업로드 및 URL 저장
        List<MultipartFile> files = postUpdateDTO.getFiles();
        List<Long> filesToDelete = postUpdateDTO.getFilesToDelete();
        handleFileUpdate(files, findPost, filesToDelete);
        postRepository.save(findPost);
        return convertToPostUpdateResponseDTO(findPost);
    }


    public void deletePost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(PostNotFoundException::new);

        List<File> files = post.getFiles();
        for (File file : files) {
            s3ImageService.deleteImageFromS3(file.getAccessUrl());
            fileRepository.delete(file);
        }
        postRepository.delete(post);
    }

    private void handleFileUpdate(List<MultipartFile> files, Post findPost, List<Long> filesToDelete) {
        if (filesToDelete != null && !filesToDelete.isEmpty()) {
            List<File> existingFiles = findPost.getFiles();

            // 삭제할 파일만 필터링
            List<File> filesToRemove = existingFiles.stream()
                    .filter(file -> filesToDelete.contains(file.getId()))
                    .collect(Collectors.toList());

            for (File fileToRemove : filesToRemove) {
                s3ImageService.deleteImageFromS3(fileToRemove.getAccessUrl());
                fileRepository.delete(fileToRemove);
                findPost.getFiles().remove(fileToRemove); // 정확한 파일 삭제
            }
        }

        if (files != null && !files.isEmpty()) {
            for (MultipartFile multipartFile : files) {
                File file = createFileEntity(multipartFile);
                file.setPost(findPost);
                fileRepository.save(file);
                findPost.addFile(file);
            }
        }
    }


    private static void validateUpdate(PostUpdateDTO postUpdateDTO) {
        // 유효성 검사
        if (postUpdateDTO.getTitle() == null || postUpdateDTO.getTitle().isEmpty()) {
            throw new IllegalArgumentException("제목은 필수입니다.");
        }
        if (postUpdateDTO.getContent() == null || postUpdateDTO.getContent().isEmpty()) {
            throw new IllegalArgumentException("내용은 필수입니다.");
        }
    }

    public File createFileEntity(MultipartFile file) {
        String imageUrl = s3ImageService.upload(file); // S3에 업로드
        File fileEntity = new File(file.getOriginalFilename());
        fileEntity.setAccessUrl(imageUrl);
        fileEntity.setFileSize(file.getSize());
        fileEntity.setFileType(file.getContentType());
        fileEntity.setFilePath(imageUrl);
        return fileEntity;
    }

    public static Post dtoToEntity(PostRequestDTO postRequestDTO, Member member) {
        return Post.builder()
                .title(postRequestDTO.getTitle())
                .content(postRequestDTO.getContent())
                .member(member)
                .created_at(LocalDateTime.now())
                .build();
    }

    public PostCheckDTO convertToPostCheckDTO(Post findPost) {
        List<Filedto> fileDTOs = findPost.getFiles().stream()
                .map(file -> new Filedto(file.getId(), file.getOriginalFilename(), file.getAccessUrl()))
                .collect(Collectors.toList());

        return new PostCheckDTO(
                findPost.getId(),
                findPost.getTitle(),
                findPost.getContent(),
                findPost.getMember().getId(),
                findPost.getCreated_at(),
                fileDTOs //파일 정보 추가
        );
    }

    public PostUpdateResponseDTO convertToPostUpdateResponseDTO(Post findPost) {
        List<File> files = findPost.getFiles();
        return new PostUpdateResponseDTO(
                findPost.getId(),
                findPost.getTitle(),
                findPost.getContent(),
                findPost.getMember().getId(),
                findPost.getUpdate_at(),
                files
        );
    }

}
