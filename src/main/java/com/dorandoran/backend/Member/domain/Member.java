package com.dorandoran.backend.Member.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;

@Entity
public class Member {

    @Id @GeneratedValue
    @JoinColumn(name = "member_id")
    private Long member_id;

    private String email;

    private String password;
}
