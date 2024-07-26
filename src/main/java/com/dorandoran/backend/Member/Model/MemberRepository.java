package com.dorandoran.backend.Member.Model;

import com.dorandoran.backend.Member.dto.MemberDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<MemberDto, Long> {
    MemberDto findByEmail(String email);
}