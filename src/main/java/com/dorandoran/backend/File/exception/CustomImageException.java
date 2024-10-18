package com.dorandoran.backend.File.exception;

import lombok.Getter;

@Getter
public class CustomImageException extends RuntimeException {
    private final String errorCode;

    // 생성자에서 ErrorCode를 String으로 변환
    public CustomImageException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode.toString(); // ErrorCode를 문자열로 변환
    }
}