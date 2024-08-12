package com.dorandoran.backend.Post.dto.check;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class PostCheckResponseDTO {
    private Long id;
    private String title;
    private LocalDateTime createdAt;
    private Long memberId;
    private List<PostCheckDTO> postCheckDTOList;
    private PostCheckDTO postCheckDTO;

    public PostCheckResponseDTO(Long id, String title, LocalDateTime createdAt, Long memberId, List<PostCheckDTO> postCheckDTOList, PostCheckDTO postCheckDTO) {
        this.id = id;
        this.title = title;
        this.createdAt = createdAt;
        this.memberId = memberId;
        this.postCheckDTOList = postCheckDTOList;
        this.postCheckDTO = postCheckDTO;
    }

    public PostCheckResponseDTO(PostCheckDTO postCheckDTO) {
        this.postCheckDTO = postCheckDTO; // 단일 게시물 DTO 초기화
    }


}
