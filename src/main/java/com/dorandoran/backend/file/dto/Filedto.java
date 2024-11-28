package com.dorandoran.backend.file.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


/**
 * 파일 생성시 필요한 DTO
 */

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "파일 생성에 필요한 데이터")
public class Filedto {

    @Schema(description = "파일 원본 이름", example = "original.jpg")
    private String originalFilename;

    @Schema(description = "저장된 파일 이름", example = "UUID...")
    private String storeFilename;

    @Schema(description = "파일 타입", example = "image/jpeg")
    private String fileType;

    @Schema(description = "파일 크기 (바이트)", example = "2048")
    private String base64Data; // base64 인코딩된 데이터
}
