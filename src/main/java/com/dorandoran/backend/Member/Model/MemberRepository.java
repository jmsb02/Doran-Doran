package com.dorandoran.backend.Member.Model;

import com.dorandoran.backend.Member.dto.MemberDto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member,Long> {
    MemberDto findByEmail(String email);
}
