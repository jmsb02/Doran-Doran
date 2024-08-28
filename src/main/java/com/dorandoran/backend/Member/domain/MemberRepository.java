package com.dorandoran.backend.Member.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Boolean existsByName(String name);
    Boolean existsByEmail(String email);
    Member findByName(String name);
    // 비밀번호 재설정 토큰으로 회원 찾기
    Optional<Member> findByResetToken(String resetToken);
    // 로그인 아이디와 이메일로 회원 찾기
    Optional<Member> findByLoginIdAndEmail(String loginId, String email);
    Optional<Member> findByLoginId(String loginId);
}