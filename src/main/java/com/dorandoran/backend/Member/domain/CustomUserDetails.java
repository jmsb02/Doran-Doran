package com.dorandoran.backend.Member.domain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;


public class CustomUserDetails implements UserDetails{
    private final Member member;

    public CustomUserDetails(Member member) {
        this.member = member;
    }

    public Member getMember() {
        return member;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return member.getPassword();
    }

    @Override
    public String getUsername() {
        return member.getName();
    }

    @Override
    public boolean isAccountNonExpired() {//계정 만료 여부
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {//계정 잠김 여부
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {//계정 자격 증명 만료 여부
        return false;
    }

    @Override
    public boolean isEnabled() {//계정 활성화 여부
        return false;
    }
}
