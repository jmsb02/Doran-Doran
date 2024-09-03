package com.dorandoran.backend.File.DTO;

import lombok.Getter;

@Getter
public class Filedto {
    private Long id;
    private String originalFilename;
    private String accessUrl;

    public Filedto(Long id, String originalFilename, String accessUrl) {
        this.id = id;
        this.originalFilename = originalFilename;
        this.accessUrl = accessUrl;
    }
}
