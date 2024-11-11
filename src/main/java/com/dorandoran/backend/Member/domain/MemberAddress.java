package com.dorandoran.backend.Member.domain;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor
public class MemberAddress {
    private double x;
    private double y;


    public MemberAddress(double x, double y) {
        this.x = x;
        this.y = y;
    }
}
