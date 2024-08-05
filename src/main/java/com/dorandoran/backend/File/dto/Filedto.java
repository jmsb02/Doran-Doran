package com.dorandoran.backend.File.DTO;

import lombok.Getter;

@Getter
public class FileDTO {
    private Long id;
    private String originalFilename;
    private String accessUrl;

    public FileDTO(Long id, String originalFilename, String accessUrl) {
        this.id = id;
        this.originalFilename = originalFilename;
        this.accessUrl = accessUrl;
    }
}
