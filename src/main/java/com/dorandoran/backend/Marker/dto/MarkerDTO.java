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

    @NotNull(message = "회원 id는 필수입니다.")
    private Long memberId;

    @NotBlank
    private String address;

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    @NotNull
    private List<MultipartFile> imageFiles;

    //기본 생성자
    public MarkerDTO() {}

    public MarkerDTO(Long memberId, String address, String title, String content, List<MultipartFile> imageFiles) {
        this.memberId = memberId;
        this.address = address;
        this.title = title;
        this.content = content;
        this.imageFiles = imageFiles;
    }
}
