package com.dorandoran.backend.Member.Controller;

import com.dorandoran.backend.Member.domain.Member;
import com.dorandoran.backend.Member.domain.MemberRepository;
import com.dorandoran.backend.Member.domain.MemberService;
import com.dorandoran.backend.Member.dto.req.LoginRequest;
import com.dorandoran.backend.Member.dto.req.MemberUpdateRequestDTO;
import com.dorandoran.backend.Member.dto.req.SignUpDTO;
import com.dorandoran.backend.Member.dto.res.MemberResponseDTO;
import com.sun.mail.imap.protocol.IMAPSaslAuthenticator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final AuthenticationManager authenticationManager;
    private final MemberService memberService;
    private final MemberRepository memberRepository;

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<Long> signUp(@RequestBody SignUpDTO signUpDTO) {
        log.info("Sign up request received: {}", signUpDTO);
        Long memberId = memberService.signUp(signUpDTO);
        return new ResponseEntity<>(memberId, HttpStatus.CREATED);
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<Long> login(@RequestBody LoginRequest loginRequest) {
        Member findMember = memberService.login(loginRequest);
        // 사용자 인증 정보를 생성
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginRequest.getLoginId(), loginRequest.getPassword());

        // AuthenticationManager를 사용하여 인증
        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        // 인증 성공 여부 확인
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        // 인증 성공 시 SecurityContext에 저장
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return ResponseEntity.ok(findMember.getId());
    }

    // 회원 정보 조회
    @GetMapping("/{memberId}")
    public ResponseEntity<MemberResponseDTO> getMemberInfo(@PathVariable Long memberId) {
        MemberResponseDTO memberInfo = memberService.getMemberInfo(memberId);
        return ResponseEntity.ok(memberInfo);
    }

    // 회원 정보 업데이트
    @PatchMapping("/{memberId}")
    public ResponseEntity<MemberResponseDTO> updateMemberInfo(@PathVariable Long memberId, @RequestBody MemberUpdateRequestDTO updateRequestDTO) {
        MemberResponseDTO updatedMember = memberService.updateMemberInfo(memberId, updateRequestDTO);
        return ResponseEntity.ok(updatedMember);
    }

    // 회원 삭제
    @DeleteMapping("/{memberId}")
    public ResponseEntity<String> deleteMember(@PathVariable Long memberId) {
        memberRepository.deleteById(memberId);
        return ResponseEntity.noContent().build();
    }

//    // 회원 삭제
//    @PostMapping("/logout")
//    public ResponseEntity<String> deleteMember() {
//        return ResponseEntity.noContent().build();
//    }
}
