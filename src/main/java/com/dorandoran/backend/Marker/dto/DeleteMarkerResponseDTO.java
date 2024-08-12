package com.dorandoran.backend.Marker.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DeleteMarkerResponseDTO {
    private String message;
    private boolean success;
}
