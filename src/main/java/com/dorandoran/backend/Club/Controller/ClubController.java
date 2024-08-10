package com.dorandoran.backend.Club.Controller;

import com.dorandoran.backend.Club.Model.ClubCommandService;
import com.dorandoran.backend.Club.dto.Clubdto;
import com.dorandoran.backend.Member.domain.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ClubController {
    private final ClubCommandService clubCommandService;
    private final MemberService memberService;

    @GetMapping("/users/clubs")
    public ResponseEntity<List<Clubdto>> getUserClubs(HttpSession session) throws Exception {
      Long memberId = (Long) session.getAttribute("memberId");
      //클럽 정보 조회
     List<Clubdto> clubs = clubCommandService.getUserClubs(memberId);
     return ResponseEntity.ok(clubs);
    }
}
