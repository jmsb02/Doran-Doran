package com.dorandoran.backend.File.Model;

import com.dorandoran.backend.File.exception.CustomImageException;
import com.dorandoran.backend.File.exception.ErrorCode;

public class FileValidator {

    /**
     * 확장자 검증 로직 - 헬퍼 클래스로 분리
     */
    public static void validateImageFileExtension(String filename) {
        int lastDotIndex = filename.lastIndexOf(".");
        if (lastDotIndex == -1) {
            throw new CustomImageException(ErrorCode.NO_FILE_EXTENSION, "파일 확장자가 없습니다.");
        }
        String extension = filename.substring(lastDotIndex + 1).toLowerCase();
        if (!FileExtension.isValidExtension(extension)) {
            throw new CustomImageException(ErrorCode.INVALID_FILE_EXTENSION, "유효하지 않은 확장자입니다.");
        }
    }
}
