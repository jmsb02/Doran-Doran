package com.dorandoran.backend.Marker.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@Getter
public class MarkerDTO {

    @NotBlank(message = "회원 id는 필수입니다.")
    private Long memberId;
    @NotBlank
    private String address;

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    @NotBlank
    private double latitude;

    @NotBlank
    private double longitude;

    @NotNull
    private List<MultipartFile> imageFiles;

    public MarkerDTO(Long memberId, String address, String title, String content, double latitude, double longitude, List<MultipartFile> files) {
        this.memberId = memberId;
        this.address = address;
        this.title = title;
        this.content = content;
        this.latitude = latitude;
        this.longitude = longitude;
        this.imageFiles = files;
    }
}
