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

    @Column(nullable = false)
    private String loginId;

    @Column(nullable = false)
    private String password;

    @Embedded
    private Address address;//주소 추가


    @Builder
    public Member(Long id, String name, String email,String loginId, String password,Address address) {
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

    /*
    * 회원 수정 로직 추가
    * */
    public void updatePassword(String newPassword) {
        this.password = newPassword;
    }

}