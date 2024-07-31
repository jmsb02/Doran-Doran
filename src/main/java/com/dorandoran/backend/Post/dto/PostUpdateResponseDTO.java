package com.dorandoran.backend.Post.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostUpdateResponseDTO {
    private Long post_id;
    private String title;
    private String content;
    private Long user_id;
    private LocalDateTime updateAt;

    public PostUpdateResponseDTO(Long post_id, String title, String content, Long user_id, LocalDateTime updateAt) {
        this.post_id = post_id;
        this.title = title;
        this.content = content;
        this.user_id = user_id;
        this.updateAt = updateAt;
    }
}
