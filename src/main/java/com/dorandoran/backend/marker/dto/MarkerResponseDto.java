package com.dorandoran.backend.marker.dto;

import com.dorandoran.backend.file.dto.Filedto;
import com.dorandoran.backend.marker.domain.MarkerAddress;
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
@Schema(description = "마커 관련 응답 반환 데이터")
public class MarkerResponseDto {

    @Schema(description = "마커 작성자", example = "더벤티")
    private String memberName;

    @Schema(description = "마커 제목", example = "더벤티")
    private String title;

    @Schema(description = "마커 내용", example = "더벤티 논현역점")
    private String content;

    @Schema(description = "마커 주소")
    private MarkerAddress address; // 또는 Address 객체

    @Schema(description = "마커 파일")
    private List<Filedto> files; // 파일 정보 DTO

}
