
package com.dorandoran.backend.Member.domain;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
public class SimpleUserDetails implements UserDetails{
    private final Member member;

    public SimpleUserDetails(Member member) {
        this.member = member;
    }

    @Override
    public String getUsername() {
        return member.getName();
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
    public boolean isAccountNonExpired() {
        //계정 만료 여부
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        //계정 잠금 여부
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        //인증 자격 증명의 만료 여부
        return true;
    }

    @Override
    public boolean isEnabled() {
        //계정 활성화 여부
        return false;
    }
}

