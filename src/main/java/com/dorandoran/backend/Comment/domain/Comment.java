package com.dorandoran.backend.Comment.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;

import java.time.LocalDateTime;

@Entity
public class Comment {

    @Id @GeneratedValue
    private Long comment_id;

    @Lob
    private String content;

    private LocalDateTime create_at;
}
