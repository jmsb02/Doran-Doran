package com.dorandoran.backend.Post.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostCheckDTO {
    private Long postId;
    private String title;
    private String content;
    private Long userId;
    private LocalDateTime createdAt;

    public PostCheckDTO(Long postId, String title, String content, Long user_id, LocalDateTime createdAt) {
        this.postId = postId;
        this.title = title;
        this.content = content;
        this.userId = user_id;
        this.createdAt = createdAt;
    }
}
