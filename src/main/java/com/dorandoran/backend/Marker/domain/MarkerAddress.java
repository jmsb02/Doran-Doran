package com.dorandoran.backend.Marker.domain;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor
public class MarkerAddress {
    private double x;
    private double y;


    public MarkerAddress(double x, double y) {
        this.x = x;
        this.y = y;
    }
}
