package com.dorandoran.backend.Marker.dto;

import com.dorandoran.backend.Member.domain.Member;

public class MarkerCheckDTO {
    private Long id;
    private Long memberId;
    private double latitude;
    private double longitude;
    private String name;

    public MarkerCheckDTO(Long id, Long memberId, double latitude, double longitude, String name) {
        this.id = id;
        this.memberId = memberId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.name = name;
    }
}
