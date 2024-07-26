package com.dorandoran.backend.Post.Model;

import com.dorandoran.backend.Member.Model.Member;
import com.dorandoran.backend.Member.Model.MemberRepository;
import com.dorandoran.backend.Member.exception.MemberNotFoundException;
import com.dorandoran.backend.Post.dto.PostDTO;
import com.dorandoran.backend.Post.dto.PostUpdateDTO;
import com.dorandoran.backend.Post.exception.PostNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


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
    public Long savePost(PostDTO postDTO) {
        Member findMember = memberRepository.findById(postDTO.getMemberId())
                .orElseThrow(() -> new MemberNotFoundException());


        Post post = Post.dtoToEntity(postDTO, findMember);

        Post savePost = postRepository.save(post);
        return savePost.getId();
    }

    /**
     * 글 상세 조회(단일)
     */
    public Post findPostOne(PostDTO postDTO) {
        Optional<Post> findPost = postRepository.findById(postDTO.getPostId());
        if(findPost.isEmpty()) {
            throw new PostNotFoundException();
        }
        return findPost.get();
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
     * 글 삭제
     */
    @Transactional
    public void deletePost(PostDTO postDTO) {
        Optional<Post> findPost = postRepository.findById(postDTO.getPostId());

        if(findPost.isEmpty()) {
            throw new PostNotFoundException();
        }

        Post post = findPost.get();
        postRepository.delete(post);
    }

}
