package com.dorandoran.backend.Member.Model;

import com.dorandoran.backend.Member.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomMemberService implements UserDetailsService {
    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        log.info("=========== security UserDetailsService");
        MemberDto user = memberRepository.findByEmail(email);
        if (user == null) throw new UsernameNotFoundException("Not Found User");
        return user;
    }
}