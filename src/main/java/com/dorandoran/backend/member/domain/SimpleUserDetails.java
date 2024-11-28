
package com.dorandoran.backend.member.domain;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
public class SimpleUserDetails implements UserDetails {

    private final Member member;

    public SimpleUserDetails(Member member) {
        this.member = member;
    }

    @Override
    public String getUsername() {
        return member.getLoginId(); // 로그인 ID로 설정
    }

    @Override
    public String getPassword() {
        return member.getPassword(); // 암호화된 비밀번호 반환
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(); // 권한 정보 추가 필요 시 설정
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // 계정 만료 여부
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // 계정 잠금 여부
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // 자격 증명 만료 여부
    }

    @Override
    public boolean isEnabled() {
        return true; // 계정 활성화 여부
    }

    // 추가 메서드
    public Long getMemberId() {
        return member.getId();
    }
}
