package com.dorandoran.backend.security.config;

import com.dorandoran.backend.Member.dto.MemberDto;
import com.dorandoran.backend.Member.Model.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomAuthProvider implements AuthenticationProvider {

    private final MemberService userService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String reqEmail = authentication.getPrincipal().toString(); // 로그인 창에 입력한 email
        String reqPassword = authentication.getCredentials().toString(); // 로그인 창에 입력한 password
        MemberDto memberDTO = (MemberDto) userService.loadUserByUsername(reqEmail);

        // 암호화되지 않은 비밀번호와 암호화된 비밀번호가 일치하는지 비교
        if (!passwordEncoder.matches(reqPassword, memberDTO.getPassword())) {
            throw new BadCredentialsException("비밀번호가 일치하지 않습니다.");
        }

        return new UsernamePasswordAuthenticationToken(reqEmail, null, memberDTO.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}