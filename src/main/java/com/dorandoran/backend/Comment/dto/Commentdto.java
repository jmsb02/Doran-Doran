package com.dorandoran.backend.Comment.dto;

import com.dorandoran.backend.Comment.Model.Comment;
import com.dorandoran.backend.reply.dto.Replydto;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class Commentdto {
    private Long commentId;
    private Long postId;
    private String content;
    private LocalDateTime createdAt;
    private List<Replydto> replies;

    @Builder
    public Commentdto(Long commentId, Long postId, String content, LocalDateTime createdAt, List<Replydto> replies) {
        this.commentId = commentId;
        this.postId = postId;
        this.content = content;
        this.createdAt = createdAt;
        this.replies = replies;
    }

    public static Commentdto CommentToDto(Comment comment) {
        return new Commentdto(
                comment.getId(),
                comment.getPost().getId(),
                comment.getContent(),
                comment.getCreated_at(),
                comment.getReplies().stream()
                        .map(reply -> new Replydto(
                                reply.getId(),
                                reply.getContent(),
                                reply.getCreated_at()
                        ))
                        .collect(Collectors.toList())
        );

    }

}
