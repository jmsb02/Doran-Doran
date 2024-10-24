package com.dorandoran.backend.Marker.dto;

import com.dorandoran.backend.File.Model.File;
import com.dorandoran.backend.Member.domain.Address;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MarkerFindDTO {
    /**
     * 마커 조회 시 필요한 DTO
     */
    private String title;
    private String content;
    private Address address; // 또는 Address 객체
    private List<File> files; // 파일 정보 DTO

}
