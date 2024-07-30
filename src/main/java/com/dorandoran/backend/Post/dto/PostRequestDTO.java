package com.dorandoran.backend.Post.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class PostRequestDTO {
    @NotNull(message = "제목은 필수입니다.")
    @Size(min = 1, max = 20, message = "제목은 1자 이상 20자 이하이여야 합니다.")
    private String title;

    @NotNull(message = "내용은 필수입니다.")
    @Size(min = 10, max = 150, message = "내용은 10자 이상 150자 이하이여야 합니다.")
    private String content;

    @NotNull(message = "회원 ID은 필수입니다.")
    private Long member_id;

    private Long file_id;

    public PostRequestDTO(String title, String content, Long member_id, Long file_id) {
        this.title = title;
        this.content = content;
        this.member_id = member_id;
        this.file_id = file_id;
    }

}
