package com.dorandoran.backend.Marker.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class MarkerFindDTO {
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
    private List<String> fileList;   // 파일 리스트 추가
    private String username;         // 사용자 이름 추가
    private String profileImg;       // 프로필 이미지 추가

    public MarkerFindDTO(Long id, Long memberId, String address, String content, String title,
                         double latitude, double longitude, List<String> fileList,
                         String username, String profileImg) {
        this.id = id;
        this.memberId = memberId;
        this.address = address;
        this.content = content;
        this.title = title;
        this.latitude = latitude;
        this.longitude = longitude;
        this.fileList = fileList;
        this.username = username;
        this.profileImg = profileImg;
    }
}
