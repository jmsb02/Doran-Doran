package com.dorandoran.backend.Member.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Boolean existsByName(String name);
    Boolean existsByEmail(String email);
    Optional<Member> findByResetToken(String resetToken);
    Optional<Member> findByLoginIdAndEmail(String loginId, String email);
    Optional<Member> findByLoginId(String loginId);
}