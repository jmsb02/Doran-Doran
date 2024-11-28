package com.dorandoran.backend.file.domain;

import lombok.Getter;

@Getter
public enum FileExtension {
    JPG("jpg", "image/jpeg"),
    JPEG("jpeg", "image/jpeg"),
    PNG("png", "image/png");

    private final String extension;
    private final String mimeType;

    FileExtension(String extension, String mimeType) {
        this.extension = extension;
        this.mimeType = mimeType;
    }

    /**
     * 주어진 파일 확장자가 ENUM으로 정의된 허용된 확장자 목록에 포함되어 있는지 확인
     */
    public static boolean isValidExtension(String ext) {
        for (FileExtension fileExtension : values()) {
            if (fileExtension.getExtension().equalsIgnoreCase(ext)) {
                return true;
            }
        }
        return false;
    }
}
