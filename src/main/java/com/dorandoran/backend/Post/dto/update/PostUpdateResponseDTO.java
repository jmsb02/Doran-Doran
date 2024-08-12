package com.dorandoran.backend.Post.dto.update;

import com.dorandoran.backend.File.Model.File;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class PostUpdateResponseDTO {
    private Long postId;
    private String title;
    private String content;
    private Long userId;
    private LocalDateTime updateAt;
    private List<File> files; // File 리스트 추가

    public PostUpdateResponseDTO(Long postId, String title, String content, Long userId, LocalDateTime updateAt, List<File> files) {
        this.postId = postId;
        this.title = title;
        this.content = content;
        this.userId = userId;
        this.updateAt = updateAt;
        this.files = files; // File 리스트 초기화
    }
}
