package com.dorandoran.backend.Post.dto.update;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@Getter
public class PostUpdateDTO {

    @NotNull(message = "제목은 필수입니다.")
    private String title;

    @Length(max = 150)
    @NotBlank(message = "글의 내용은 필수입니다.")
    private String content;

    @NotNull
    private List<MultipartFile> files;

    private List<Long> filesToDelete; // 삭제할 파일 ID 목록 추가

    public PostUpdateDTO(String title, String content, List<MultipartFile> files) {
        this.title = title;
        this.content = content;
        this.files = files;
    }
}
