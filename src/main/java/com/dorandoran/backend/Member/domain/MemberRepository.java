package com.dorandoran.backend.Member.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    boolean existsByLoginId(String loginId);
    boolean existsByName(String name);
    boolean existsByEmail(String email);
    Optional<Member> findByLoginId(String loginId);
}