package com.dorandoran.backend.session.controller;

import com.dorandoran.backend.Member.domain.Member;
import com.dorandoran.backend.Member.domain.MemberService;
import com.dorandoran.backend.Member.exception.MemberNotFoundException;
import com.dorandoran.backend.session.dto.JoinRequest;
import com.dorandoran.backend.session.dto.LoginRequest;
import com.dorandoran.backend.session.dto.SendResetPasswordEmailReq;
import com.dorandoran.backend.session.dto.SendResetPasswordEmailRes;
import com.dorandoran.backend.session.service.MailService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class SessionController {
    private final MemberService memberService;
    private final MailService mailService;

    @GetMapping("/")
    public ResponseEntity<String> home(HttpServletRequest httpServletRequest) {
        HttpSession session = httpServletRequest.getSession(false);
        if (session == null || session.getAttribute("memberId") == null) {
            return loginPage();
        } else {
            Long memberId = (Long) session.getAttribute("memberId");
            Member loginMember = memberService.getLoginMemberById(memberId);
            return ResponseEntity.ok("Main Page,Hello " + loginMember.getName());
        }
    }

    @GetMapping("/join")
    public ResponseEntity<String> joinPage() {
        return ResponseEntity.ok("Join page");
    }

    @PostMapping("/join")
    public ResponseEntity<String> join(@Valid @RequestBody JoinRequest joinRequest, BindingResult bindingResult) {
        memberService.join(joinRequest, bindingResult);

        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Validation errors: " + bindingResult.getAllErrors());
        }

        return ResponseEntity.status(HttpStatus.CREATED).body("회원가입 성공");
    }

    @GetMapping("/login")
    public ResponseEntity<String> loginPage() {
        return ResponseEntity.ok("Login page");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginRequest loginRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) {
        try {
            Member member = memberService.login(loginRequest);

            // 로그인 성공 시 세션 생성
            httpServletRequest.getSession().invalidate();
            HttpSession session = httpServletRequest.getSession(true);
            session.setAttribute("memberId", member.getId());
            session.setMaxInactiveInterval(1800);

            return ResponseEntity.ok("로그인 성공");
        } catch (MemberNotFoundException e) {
            bindingResult.reject("loginfail", e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 실패: " + e.getMessage());
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest httpServletRequest) {
        HttpSession session = httpServletRequest.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return ResponseEntity.ok("로그아웃 합니다.");
    }

    /*
    * 비밀번호 재설정 이메일 전송
    * */
    @PostMapping("/reset-password")
    public SendResetPasswordEmailRes sendResetPassword(@Validated @RequestBody SendResetPasswordEmailReq resetPasswordEmailReq)
    {
        memberService.checkMemberByEmail(resetPasswordEmailReq.getEmail());
        String uuid = mailService.sendResetPasswordEmail(resetPasswordEmailReq.getEmail());
        return SendResetPasswordEmailRes.builder()
                .UUID(uuid)
                .build();
    }

    /*
    *
    비밀번호 재설정*/
    @PostMapping("/reset-password/{uuid}")
    public ResponseEntity<String> resetPassword(@PathVariable("uuid") String uuid,@RequestBody String newPassword){
        memberService.resetPassword(uuid, newPassword);
        return ResponseEntity.ok("비밀번호가 성공적으로 재설정 되었습니다.");
    }


}