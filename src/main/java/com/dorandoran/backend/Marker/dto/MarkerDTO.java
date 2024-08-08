package com.dorandoran.backend.Marker.dto;


import com.dorandoran.backend.Member.domain.Member;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
public class MarkerDTO {

    @NotBlank
    private String author;

    @NotBlank
    private double latitude;

    @NotBlank
    private double longitude;

    @NotBlank(message = "회원 id는 필수입니다.")
    private Long member_id;

    @NotBlank
    private String name;

    @NotNull
    private List<MultipartFile> files;


    // 매개변수가 있는 생성자

    public MarkerDTO(String author, String name, double latitude, double longitude, Long member_id) {
        this.author = author;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.member_id = member_id;

    }


    // 매개변수가 있는 생성자

}
