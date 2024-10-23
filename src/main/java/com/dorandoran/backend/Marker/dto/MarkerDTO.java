package com.dorandoran.backend.Marker.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
public class MarkerDTO {

    /**
     * 마커 생성시 필요한 DTO
     */

    @NotBlank(message = "제목은 필수입니다.")
    private String title;

    @NotBlank(message = "내용은 필수입니다.")
    private String content;

    @NotNull
    private List<MultipartFile> imageFiles;

    //기본 생성자
    public MarkerDTO() {}

    public MarkerDTO(String title, String content, List<MultipartFile> files) {
        this.title = title;
        this.content = content;
        this.imageFiles = files;
    }
}
