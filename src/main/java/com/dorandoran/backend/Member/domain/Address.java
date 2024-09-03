package com.dorandoran.backend.Member.domain;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor
public class Address {
    private double x;
    private double y;


    public Address(double x, double y) {
        this.x = x;
        this.y = y;
    }
}
