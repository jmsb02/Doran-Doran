//package com.dorandoran.backend.Member.domain;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//@Service
//@RequiredArgsConstructor
//public class MemberDetailService implements UserDetailsService {
//
//    private final MemberRepository memberRepository;
//
//    @Override
//    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
//        Member member = memberRepository.findByLoginId(loginId)
//                .orElseThrow(() -> new UsernameNotFoundException("User not found with loginId: " + loginId));
//        return new CustomUserDetails(member);
//    }
//}