package com.dorandoran.backend.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Schema(description = "에러 응답")
public class ErrorResponse {

    @Schema(description = "오류 메세지", example = "잘못된 요청입니다.")
    private String message;

    @Schema(description = "오류 코드", example = "400")
    private int statusCode;
}
