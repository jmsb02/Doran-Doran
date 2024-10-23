package com.dorandoran.backend.File.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {
    /**
     * 파일 관련 에러 코드
     */
    EMPTY_FILE_EXCEPTION("파일이 비어 있습니다."),
    IO_EXCEPTION_ON_IMAGE_UPLOAD("이미지 업로드 중 IO 예외 발생."),
    NO_FILE_EXTENSION("파일 확장자가 없습니다."),
    INVALID_FILE_EXTENSION("유효하지 않은 파일 확장자입니다."),
    INVALID_IMAGE_ADDRESS("이미지 주소가 유효하지 않습니다."),
    FILE_SIZE_EXCEPTION("파일 크기는 5MB를 초과할 수 없습니다."),
    INVALID_FILE_TYPE_EXCEPTION("허용되지 않는 파일 형식입니다."),
    INVALID_BASE64_FORMAT("잘못된 Base64 형식입니다."),
    MISSING_DATA_PART("Base64 문자열에 데이터 부분이 누락되었습니다."),
    UNSUPPORTED_IMAGE_TYPE("지원하지 않는 이미지 타입입니다."),

    /**
     * 마커 관련 에러 코드
     */
    MAX_PHOTO_LIMIT_EXCEPTION("사진은 최대 3개까지만 업로드할 수 있습니다.");


    private final String message;

    ErrorCode(String message) {
        this.message = message;
    }

}
