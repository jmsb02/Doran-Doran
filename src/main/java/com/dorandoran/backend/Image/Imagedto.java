package com.dorandoran.backend.Image;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Imagedto {
    private String name;
    private String type;
    private String base64Data;
}
