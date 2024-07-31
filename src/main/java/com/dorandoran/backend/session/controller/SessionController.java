package com.dorandoran.backend.session.controller;

import com.dorandoran.backend.Member.domain.Member;
import com.dorandoran.backend.Member.domain.MemberService;
import com.dorandoran.backend.Member.dto.JoinRequest;
import com.dorandoran.backend.Member.dto.LoginRequest;
import com.dorandoran.backend.Member.exception.MemberNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/session")
public class SessionController {
    private final MemberService memberService;

    @GetMapping("/")
    public ResponseEntity<String> home(@SessionAttribute(name = "memberId", required = false) Long memberId) {
        try {
            Member loginMember = memberService.getLoginMemberById(memberId);
            return ResponseEntity.ok("Hello, " + loginMember.getName());
        } catch (MemberNotFoundException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Not logged in");
        }
    }

    @GetMapping("/join")
    public ResponseEntity<String> joinPage() {
        return ResponseEntity.ok("Join page");
    }

    @PostMapping("/join")
    public ResponseEntity<String> join(@Valid @RequestBody JoinRequest joinRequest, BindingResult bindingResult) {
        if (memberService.checkLoginIdDuplicate(joinRequest.getLoginId())) {
            bindingResult.addError(new FieldError("joinRequest", "loginId", "로그인 아이디가 중복됩니다."));
        }

        if (memberService.checkNameDuplicate(joinRequest.getName())) {
            bindingResult.addError(new FieldError("joinRequest", "name", "닉네임이 중복됩니다."));
        }

        if (!joinRequest.getPassword().equals(joinRequest.getPasswordCheck())) {
            bindingResult.addError(new FieldError("joinRequest", "passwordCheck", "비밀번호가 일치하지 않습니다"));
        }

        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Validation errors");
        }

        memberService.join(joinRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body("회원가입 성공");
    }

    @GetMapping("/login")
    public ResponseEntity<String> loginPage() {
        return ResponseEntity.ok("Login page");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginRequest loginRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest) {
        Optional<Member> member = memberService.login(loginRequest);

        if (member.isEmpty()) {
            bindingResult.reject("loginfail", "로그인 아이디 또는 비밀번호가 틀렸습니다.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 실패: 아이디 또는 비밀번호가 틀렸습니다.");
        }

        // 로그인 성공 시 세션 생성
        httpServletRequest.getSession().invalidate();
        HttpSession session = httpServletRequest.getSession(true);
        session.setAttribute("memberId", member.get().getId());
        session.setMaxInactiveInterval(1800);

        return ResponseEntity.ok("로그인 성공");
    }

    @GetMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest httpServletRequest) {
        HttpSession session = httpServletRequest.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return ResponseEntity.ok("로그아웃 성공");
    }
}