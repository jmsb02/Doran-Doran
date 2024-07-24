package com.dorandoran.backend.Post.Model;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PostCommandService {

    private final PostRepository postRepository;

    /**
     * 글 작성 폼 표시
     */


    /**
     * 글 상세 조회(단일)
     */

    /**
     * 글 목록 조회
     */

    /**
     * 글 수정
     */
    private void UpdatePost(String title, String content) {

    }
    /**
     * 글 삭제
     */
}
