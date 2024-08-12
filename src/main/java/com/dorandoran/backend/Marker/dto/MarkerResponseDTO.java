package com.dorandoran.backend.Marker.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MarkerResponseDTO {
    /**
     * 마커 작성 성공시 아이디와 메세지 반환 메서드
     */
    private Long markerId;
    private String message;
}
