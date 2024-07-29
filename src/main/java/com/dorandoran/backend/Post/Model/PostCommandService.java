package com.dorandoran.backend.Post.Model;

import com.dorandoran.backend.Member.domain.Member;
import com.dorandoran.backend.Member.domain.MemberRepository;
import com.dorandoran.backend.Member.exception.MemberNotFoundException;
import com.dorandoran.backend.Post.dto.PostCheckDTO;
import com.dorandoran.backend.Post.dto.PostRequestDTO;
import com.dorandoran.backend.Post.dto.PostUpdateDTO;
import com.dorandoran.backend.Post.exception.PostNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

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
                .orElseThrow(() -> new MemberNotFoundException());


        Post post = Post.dtoToEntity(postRequestDTO, findMember);

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
        Optional<Post> findPost = postRepository.findById(post_id);
        if(findPost.isEmpty()) {
            throw new PostNotFoundException();
        }
        Post post = findPost.get();

        return new PostCheckDTO(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getMember().getId(),
                post.getCreated_at()
        );
    }

    /**
     * 글 목록 조회
     */
    public Page<Post> findPostAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return postRepository.findAll(pageable);
    }

    /**
     * 게시물 수정
     */
    @Transactional
    public void updatePost(Long postId, PostUpdateDTO postUpdateDTO) {
        Optional<Post> findPost = postRepository.findById(postId);
        if(findPost.isEmpty()) {
            throw new PostNotFoundException();
        }

        Post post = findPost.get();
        post.update(postUpdateDTO.getTitle(), postUpdateDTO.getContent());
        postRepository.save(post);
    }



    /**
     * 글 삭제시 응답 API 처리
     */
    @Transactional
    public Map<String, Object> deletePost(Long post_id) {
        Map<String, Object> response = new HashMap<>();
        Optional<Post> findPost = postRepository.findById(post_id);

        if(findPost.isEmpty()) {
            throw new PostNotFoundException();
        }
        Post post = findPost.get();
        postRepository.delete(post);

        response.put("success", "true");
        return response;
    }
}
