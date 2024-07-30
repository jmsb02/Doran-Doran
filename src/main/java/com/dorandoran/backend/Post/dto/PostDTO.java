package com.dorandoran.backend.Post.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

@Getter
public class PostDTO {

    @NotNull(message = "제목은 필수입니다.")
    private String title;

    @Length(max = 150)
    @NotBlank(message = "내용은 필수입니다.")
    private String content;

    @NotNull
    private Long memberId;

    @NotNull
    private Long postId;

}
