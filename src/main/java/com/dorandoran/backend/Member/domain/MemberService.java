package com.dorandoran.backend.Member.domain;

import com.dorandoran.backend.Member.dto.JoinRequest;
import com.dorandoran.backend.Member.dto.LoginRequest;
import com.dorandoran.backend.Member.exception.MemberNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    // 닉네임 중복 체크
    public boolean checkNameDuplicate(String name) {
        return memberRepository.existsByName(name);
    }

    // 로그인Id 중복 체크
    public boolean checkLoginIdDuplicate(String loginId) {
        return memberRepository.existsByLoginId(loginId);
    }

    // 회원가입
    public void join(JoinRequest joinRequest) {
        String hashedPassword = BCrypt.hashpw(joinRequest.getPassword(), BCrypt.gensalt());
        memberRepository.save(joinRequest.toEntity(hashedPassword));
    }

    // 로그인
    public Optional<Member> login(LoginRequest loginRequest) {
        return memberRepository.findByLoginId(loginRequest.getLoginId())
                .filter(member -> BCrypt.checkpw(loginRequest.getPassword(), member.getPassword()));
    }

    // memberId를 입력받아 member를 리턴해주는 기능
    public Member getLoginMemberById(Long memberId) {
        return Optional.ofNullable(memberId)
                .flatMap(memberRepository::findById)
                .orElseThrow(() -> new MemberNotFoundException("Member not found with ID: " + memberId));
    }

    // loginId를 입력받아 member를 리턴해주는 기능
    public Member getLoginMemberByLoginId(String loginId) {
        return Optional.ofNullable(loginId)
                .flatMap(memberRepository::findByLoginId)
                .orElseThrow(() -> new MemberNotFoundException("Member not found with login ID: " + loginId));
    }
}