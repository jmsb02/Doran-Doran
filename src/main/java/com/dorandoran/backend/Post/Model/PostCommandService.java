package com.dorandoran.backend.Post.Model;

import com.dorandoran.backend.Member.domain.Member;
import com.dorandoran.backend.Member.domain.MemberRepository;
import com.dorandoran.backend.Member.exception.MemberNotFoundException;
import com.dorandoran.backend.Post.dto.*;
import com.dorandoran.backend.Post.exception.PostNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostCommandService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    /**
     * 게시물 저장
     */
    public Long savePost(PostRequestDTO postRequestDTO) {
        Member findMember = memberRepository.findById(postRequestDTO.getMember_id())
                .orElseThrow(() -> new MemberNotFoundException("멤버가 존재하지 않습니다."));


        Post post = dtoToEntity(postRequestDTO, findMember);

        Post savePost = postRepository.save(post);
        return savePost.getId();
    }

    /**
     * 게시물 저장 시 응답 API 처리 부분
     */
    public ResponseEntity<Map<String, Object>> createPost(PostRequestDTO postRequestDTO) {
        Map<String,Object> response = new HashMap<>();
        try {
            Long post_id = savePost(postRequestDTO);
            response.put("success","true");
            response.put("post_id", post_id);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", "false");
            response.put("error", "작성 실패");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * 글 상세 조회(단일)
     */
    public PostCheckDTO findPostOne(Long post_id) {
        Post find_post = postRepository.findById(post_id)
                .orElseThrow(() -> new PostNotFoundException());


        return new PostCheckDTO(
                find_post.getId(),
                find_post.getTitle(),
                find_post.getContent(),
                find_post.getMember().getId(),
                find_post.getCreated_at()
        );
    }

    /**
     * 글 목록 조회
     */
    public PostCheckResponseDTO getPosts(int page, int pageSize) {
        PageRequest pageable = PageRequest.of(page, pageSize);
        Page<Post> postPage = postRepository.findAll(pageable);
        List<PostCheckDTO> posts = postPage.getContent().stream()
                .map(post -> new PostCheckDTO(post.getId(), post.getTitle(), post.getContent(), post.getMember().getId(), post.getCreated_at()))
                .toList();

        return new PostCheckResponseDTO(page, pageSize, postPage.getTotalElements(), postPage.getTotalPages(), posts);
    }

    /**
     * 게시물 수정
     */
    @Transactional
    public PostUpdateResponseDTO updatePost(Long postId, PostUpdateDTO postUpdateDTO) {
        Post findPost = postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException());

        findPost.update(postUpdateDTO.getTitle(), postUpdateDTO.getContent());
        postRepository.save(findPost);

        return new PostUpdateResponseDTO(
                findPost.getId(),
                findPost.getTitle(),
                findPost.getContent(),
                findPost.getMember().getId(),
                findPost.getUpdate_at()
        );
    }



    /**
     * 글 삭제시 응답 API 처리
     */
    @Transactional
    public Map<String, Object> deletePost(Long post_id) {
        Map<String, Object> response = new HashMap<>();
        Post findPost = postRepository.findById(post_id)
                .orElseThrow(() -> new PostNotFoundException());

        postRepository.delete(findPost);

        response.put("success", "true");
        return response;
    }

    public static Post dtoToEntity(PostRequestDTO postRequestDTO, Member member) {
        return Post.builder()
                .title(postRequestDTO.getTitle())
                .content(postRequestDTO.getContent())
                .member(member)
                .created_at(LocalDateTime.now()).build();
    }
}
