package com.dorandoran.backend.Club.dto;

import com.dorandoran.backend.Marker.Model.Marker;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Clubdto {
    private Long id;
    private String name;
    private String description;
    private Marker marker;

    public Clubdto(Long id, String name, String description, Marker marker) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.marker = marker;
    }
}
