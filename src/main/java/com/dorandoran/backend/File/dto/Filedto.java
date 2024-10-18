package com.dorandoran.backend.File.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor

public class Filedto {
    private Long id;
    private String originalFilename;
    private String accessUrl;

}
