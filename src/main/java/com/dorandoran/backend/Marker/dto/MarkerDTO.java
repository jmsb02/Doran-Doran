package com.dorandoran.backend.Marker.dto;


import com.dorandoran.backend.Member.domain.Member;
import lombok.Getter;

@Getter
public class MarkerDTO {
    private Long id;
    private String author;
    private double latitude;
    private double longitude;
    private Long member_id;
    private String name;


    // 매개변수가 있는 생성자

    public MarkerDTO(Long id,String author, String name, double latitude, double longitude, Long member_id) {
        this.id = id;
        this.author = author;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.member_id = member_id;

    }


    // 매개변수가 있는 생성자

}
