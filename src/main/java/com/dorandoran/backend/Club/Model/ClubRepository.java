package com.dorandoran.backend.Club.Model;

import com.dorandoran.backend.Member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClubRepository extends JpaRepository<Club, Long> {
    List<Club> findAllByMember(Member member);
}
