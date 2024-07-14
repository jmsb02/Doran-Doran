package com.dorandoran.backend.Post.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;

import java.time.LocalDateTime;

@Entity
public class Post {

    @Id @GeneratedValue
    private Long post_id;

    @Lob
    private String content;

    private LocalDateTime create_at;
}
