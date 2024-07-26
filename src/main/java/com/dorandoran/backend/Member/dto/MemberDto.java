package com.dorandoran.backend.Member.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
public class MemberDto implements UserDetails {
    @Id
    private Long id;
    private String email;
    private String password;
    private String nickname;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password){
        this.password = password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    //계정 만료 여부(true:만료되지 않음, false:만료됨)
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // 계정 잠금 여부(true: 계정잠금아님, false: 계정잠금상태)
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // 계정 패스워드 만료 여부(true: 만료되지 않음, false: 만료됨)
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // 계정 사용가능 여부(true: 사용가능, false: 사용불가능)
    @Override
    public boolean isEnabled() {
        return true;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}