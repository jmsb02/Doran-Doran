package com.dorandoran.backend.Post.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostCheckDTO {
    private Long post_id;
    private String title;
    private String content;
    private Long user_id;
    private LocalDateTime createdAt;

    public PostCheckDTO(Long post_id, String title, String content, Long user_id, LocalDateTime createdAt) {
        this.post_id = post_id;
        this.title = title;
        this.content = content;
        this.user_id = user_id;
        this.createdAt = createdAt;
    }
}
