package com.dorandoran.backend.Post.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostSummaryDTO {
    private Long id;
    private String title;
    private LocalDateTime createdAt;
    private Long memberId;

    public PostSummaryDTO(Long id, String title, LocalDateTime createdAt, Long memberId) {
        this.id = id;
        this.title = title;
        this.createdAt = createdAt;
        this.memberId = memberId;
    }
}
