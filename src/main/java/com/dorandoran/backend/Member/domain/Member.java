package com.dorandoran.backend.Member.domain;

import com.dorandoran.backend.Marker.Model.Marker;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false, unique = true)
    private String loginId;

    // 비밀번호 설정 메서드
    @Setter
    @Column(nullable = false)
    private String password;

    @Embedded
    private Address address;

    // 비밀번호 재설정 토큰 설정 메서드
    @Setter
    private String resetToken;

    @Column(nullable = true)
    private String profileImg;

    @Builder
    public Member(Long id, String name, String email, String loginId, String password, Address address,String profileImg) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.loginId = loginId;
        this.password = password;
        this.address = address;
        this.profileImg = profileImg;
    }
    // 비밀번호만 업데이트하는 메서드 추가

    public void updatePassword(String password) {
        this.password = password;
    }

    public void update(String name, Address address, String email) {
        this.name = name;
        this.address = address;
        this.email = email;
    }
}