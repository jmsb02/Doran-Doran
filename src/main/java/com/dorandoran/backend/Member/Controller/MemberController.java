package com.dorandoran.backend.Member.Controller;

import com.dorandoran.backend.Member.domain.MemberService;
import com.dorandoran.backend.Member.dto.req.*;
import com.dorandoran.backend.Member.dto.res.MemberResponseDTO;
import com.dorandoran.backend.Member.dto.res.SendResetPasswordRes;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/")
    public ResponseEntity<String> home(HttpSession session) {
        String result = memberService.determineHomePage(session);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/signup")
    public void signup(@RequestBody SignUpRequest signUpRequest) throws Exception {
        memberService.signUp(signUpRequest);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginRequest loginRequest,HttpSession session) {
        memberService.login(loginRequest,session);
        return ResponseEntity.ok("로그인에 성공하였습니다.");
    }

    @GetMapping("/logout1")
    public void logout(HttpSession session) {
        session.invalidate();
    }


    @GetMapping("/find-id")
    public ResponseEntity<String> findLoginId(@RequestParam String email) {
        String result = memberService.findLoginIdByEmail(email);
        return ResponseEntity.ok(result);
    }


    @GetMapping("/reset-password")
    public ResponseEntity<SendResetPasswordRes> sendResetPassword(@Validated @RequestBody SendResetPasswordReq resetPasswordEmailReq) {
        SendResetPasswordRes response = memberService.sendResetPassword(resetPasswordEmailReq);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/reset-password/{uuid}")
    public ResponseEntity<String> resetPassword(@PathVariable("uuid") String uuid,@RequestBody ResetPasswordReq newPassword){
        memberService.resetPassword(uuid, newPassword.getNewPassword());
        return ResponseEntity.ok("비밀번호가 성공적으로 재설정 되었습니다.");
    }

    @GetMapping("/users")
    public ResponseEntity<MemberResponseDTO> getUserInfo(HttpSession session){
        return memberService.getCurrentUserInfo(session);
    }

    @PutMapping("/users")
    public ResponseEntity<MemberResponseDTO> updateUserInfo(@RequestBody MemberUpdateRequestDTO memberUpdateRequestDTO,HttpSession session){
        Long memberId = (Long) session.getAttribute("memberId");
        return memberService.updateMemberInfo(memberUpdateRequestDTO,memberId);
    }

    @DeleteMapping("/users")
    public void deleteUserInfo(HttpSession session) {
        Long memberId = (Long) session.getAttribute("memberId");
        session.invalidate();
        memberService.deleteMember(memberId);
    }
}