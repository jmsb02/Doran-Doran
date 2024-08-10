package com.dorandoran.backend.Club.Model;

import com.dorandoran.backend.Club.dto.Clubdto;
import com.dorandoran.backend.Club.exception.ClubNotFoundException;
import com.dorandoran.backend.Member.domain.Member;
import com.dorandoran.backend.Member.domain.MemberRepository;
import com.dorandoran.backend.Member.exception.MemberNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Transactional
public class ClubCommandService {
    private final MemberRepository memberRepository;
    private final ClubRepository clubRepository;

    public List<Clubdto> getUserClubs(Long memberId) throws Exception{
        Member member = memberRepository.findById(memberId)
                .orElseThrow(MemberNotFoundException::new);

       List<Club> clubs = clubRepository.findAllByMember(member);
       if(clubs.isEmpty()) {
           throw new ClubNotFoundException("해당 멤버가 가입한 클럽이 없습니다.");
       }
       return clubs.stream()
               .map(club -> new Clubdto(
                       club.getId(),
                       club.getName(),
                       club.getDescription(),
                       club.getMarker()
               ))
               .collect(Collectors.toList());
    }
}
