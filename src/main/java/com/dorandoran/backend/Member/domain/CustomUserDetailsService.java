package com.dorandoran.backend.Member.domain;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    public CustomUserDetailsService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Member member = memberRepository.findByName(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));


        log.info("User found: {}", member.getName());

        return new SimpleUserDetails(member); // CustomUserDetails는 UserDetails를 구현한 클래스
    }
}
