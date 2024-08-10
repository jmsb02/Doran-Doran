package com.dorandoran.backend.Reply.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Replydto {
    private Long replyId;
    private String content;
    private LocalDateTime createdAt;

    public Replydto(Long replyId, String content, LocalDateTime createdAt) {
        this.replyId = replyId;
        this.content = content;
        this.createdAt = createdAt;
    }
}
