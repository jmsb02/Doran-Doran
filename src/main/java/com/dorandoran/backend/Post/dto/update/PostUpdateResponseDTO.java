package com.dorandoran.backend.Post.dto.update;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostUpdateResponseDTO {
    private Long postId;
    private String title;
    private String content;
    private Long userId;
    private LocalDateTime updateAt;

    public PostUpdateResponseDTO(Long postId, String title, String content, Long userId, LocalDateTime updateAt) {
        this.postId = postId;
        this.title = title;
        this.content = content;
        this.userId = userId;
        this.updateAt = updateAt;
    }
}
