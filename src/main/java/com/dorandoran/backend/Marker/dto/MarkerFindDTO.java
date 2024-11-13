package com.dorandoran.backend.Marker.dto;

import com.dorandoran.backend.File.DTO.FileDTO;
import com.dorandoran.backend.Marker.domain.MarkerAddress;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;


/**
 * 마커 조회 시 필요한 DTO
 */

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MarkerFindDTO {

    @Schema(description = "마커 제목", example = "더벤티")
    private String title;

    @Schema(description = "마커 내용", example = "더벤티 논현역점")
    private String content;

    @Schema(description = "마커 주소")
    private MarkerAddress address; // 또는 Address 객체

    @Schema(description = "마커 파일")
    private List<FileDTO> files; // 파일 정보 DTO

}
