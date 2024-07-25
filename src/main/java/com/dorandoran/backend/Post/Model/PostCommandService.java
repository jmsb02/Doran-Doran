package com.dorandoran.backend.Post.Model;

import com.dorandoran.backend.Member.Model.Member;
import com.dorandoran.backend.Member.Model.MemberRepository;
import com.dorandoran.backend.Member.exception.MemberNotFoundException;
import com.dorandoran.backend.Post.dto.PostDTO;
import com.dorandoran.backend.Post.dto.PostUpdateDTO;
import com.dorandoran.backend.Post.exception.PostNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PostCommandService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    /**
     * 게시물 저장
     */
    private Long savePost(PostDTO postDTO) {
        Optional<Member> findMember = memberRepository.findById(postDTO.getMemberId());

        if (findMember.isEmpty()) {
            throw new MemberNotFoundException("멤버가 존재하지 않습니다.");
        }

        Post post = Post.builder()
                .title(postDTO.getTitle())
                .content(postDTO.getContent())
                .created_at(LocalDateTime.now())
                .member(findMember.get())
                .build();

        Post savePost = postRepository.save(post);
        return savePost.getId();
    }

    /**
     * 글 상세 조회(단일)
     */
    private void findPost(PostDTO postDTO) {

    }

    /**
     * 글 목록 조회
     */


    /**
     * 게시물 수정
     */
    private void updatePost(Long postId, PostUpdateDTO postUpdateDTO) {
        Optional<Post> findPost = postRepository.findById(postId);
        if(findPost.isEmpty()) {
            throw new PostNotFoundException("게시물이 존재하지 않습니다.");
        }

        Post post = findPost.get();
        post.update(postUpdateDTO.getTitle(), postUpdateDTO.getContent());
        postRepository.save(post);

    }
    /**
     * 글 삭제
     */
    private void deletePost(long postId) {
        Optional<Post> findPost = postRepository.findById(postId);

        if(findPost.isEmpty()) {
            throw new PostNotFoundException("게시물이 존재하지 않습니다.");
        }

        Post post = findPost.get();
        postRepository.delete(post);
    }

}
