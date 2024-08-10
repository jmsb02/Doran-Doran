package com.dorandoran.backend.Member.domain;

import com.dorandoran.backend.Marker.Model.Marker;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    @Column(nullable = false)
    private String password;

    @Embedded
    private Address address;

    private String resetToken;

    @Builder
    public Member(Long id, String name, String email, String loginId, String password, Address address) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.loginId = loginId;
        this.password = password;
        this.address = address;
    }

    @OneToMany
    @JoinColumn(name = "member_id")
    private Set<Marker> markers;

    // 비밀번호 설정 메서드
    public void setPassword(String password) {
        this.password = password;
    }

    // 비밀번호 재설정 토큰 설정 메서드
    public void setResetToken(String resetToken) {
        this.resetToken = resetToken;
    }

    public void update(String name, Address address, String email, String password) {
        this.name = name;
        this.address = address;
        this.email = email;
        this.password = password;
    }
}