package com.dorandoran.backend.Member.Controller;

import com.dorandoran.backend.Member.domain.MemberService;
import com.dorandoran.backend.Member.domain.SimpleUserDetails;
import com.dorandoran.backend.Member.dto.req.LoginRequest;
import com.dorandoran.backend.Member.dto.req.MemberUpdateRequestDTO;
import com.dorandoran.backend.Member.dto.req.SignUpDTO;
import com.dorandoran.backend.Member.dto.res.MemberResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<Long> signUp(@RequestBody @Valid SignUpDTO signUpDTO) {
        Long memberId = memberService.signUp(signUpDTO);
        return new ResponseEntity<>(memberId, HttpStatus.CREATED);
    }



    @PostMapping("/login")
    public ResponseEntity<MemberResponseDTO> login(@RequestBody @Valid LoginRequest loginRequest, HttpServletRequest httpRequest) {
        MemberResponseDTO responseDTO = memberService.login(loginRequest, httpRequest);
        return ResponseEntity.ok(responseDTO);
        }

    // 회원 정보 조회
    @GetMapping("/findMember")
    public ResponseEntity<MemberResponseDTO> getMemberInfo(@AuthenticationPrincipal SimpleUserDetails userDetails) {
        Long memberId = userDetails.getMemberId();
        MemberResponseDTO memberInfo = memberService.getMemberInfo(memberId);
        return ResponseEntity.ok(memberInfo);
    }

    // 회원 정보 업데이트
    @PatchMapping("/updateMember")
    public ResponseEntity<MemberResponseDTO> updateMemberInfo(@AuthenticationPrincipal SimpleUserDetails userDetails, @RequestBody MemberUpdateRequestDTO updateRequestDTO) {
        Long memberId = userDetails.getMemberId();
        MemberResponseDTO updatedMember = memberService.updateMemberInfo(memberId, updateRequestDTO);
        return ResponseEntity.ok(updatedMember);
    }

    // 회원 삭제
    @DeleteMapping("/deleteMember")
    public ResponseEntity<String> deleteMember(@AuthenticationPrincipal SimpleUserDetails userDetails) {
        Long memberId = userDetails.getMemberId();
        memberService.deleteMember(memberId);
        return ResponseEntity.noContent().build();
    }


}
