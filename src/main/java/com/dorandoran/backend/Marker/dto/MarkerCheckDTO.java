package com.dorandoran.backend.Marker.dto;

import lombok.Getter;

@Getter
public class MarkerCheckDTO {
    /**
     * 마커 조회 시 필요한 DTO
     */
    private Long id;
    private Long memberId;
    private String address;
    private String content;
    private String title;
    private double latitude;
    private double longitude;

    public MarkerCheckDTO(Long id, Long memberId, String address, String content, String title, double latitude, double longitude) {
        this.id = id;
        this.memberId = memberId;
        this.address = address;
        this.content = content;
        this.title = title;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
