package com.dorandoran.backend.Post.dto;

import com.dorandoran.backend.File.DTO.FileDTO;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class PostCheckDTO {
    private Long postId;
    private String title;
    private String content;
    private Long memberId;
    private LocalDateTime createdAt;
    private List<FileDTO> files; // 파일 정보를 위한 리스트 추가


    public PostCheckDTO(Long postId, String title, String content, Long memberId, LocalDateTime createdAt, List<FileDTO> files) {
        this.postId = postId;
        this.title = title;
        this.content = content;
        this.memberId = memberId;
        this.createdAt = createdAt;
        this.files = files; // 파일 리스트 초기화
    }

}
