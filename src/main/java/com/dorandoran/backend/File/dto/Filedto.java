package com.dorandoran.backend.File.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FileDTO {

    private String originalFilename;
    private String storeFilename;
    private String fileType;
    private String base64Data; // base64 인코딩된 데이터

}
