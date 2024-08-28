package com.dorandoran.backend.Member.Controller;

import com.dorandoran.backend.Member.domain.MemberService;
import com.dorandoran.backend.Member.dto.req.LoginRequest;
import com.dorandoran.backend.Member.dto.req.MemberUpdateRequestDTO;
import com.dorandoran.backend.Member.dto.req.SendResetPasswordReq;
import com.dorandoran.backend.Member.dto.req.SignUpRequest;
import com.dorandoran.backend.Member.dto.res.MemberResponseDTO;
import com.dorandoran.backend.Member.dto.res.SendResetPasswordRes;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@Valid @RequestBody SignUpRequest signUpRequest) {
        memberService.signUp(signUpRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body("회원가입이 완료되었습니다");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginRequest loginRequest) {
        memberService.login(loginRequest);
        return ResponseEntity.ok("Login success");
    }

    @GetMapping("/logout")
    public void logout(HttpSession session) {
        session.invalidate();
    }

    @GetMapping("/find-id")
    public ResponseEntity<String> findLoginId(@RequestParam("token") String token) {
        String result = memberService.findLoginIdByEmail(token);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/reset-password")
    public ResponseEntity<SendResetPasswordRes> sendResetPassword(@Validated @RequestBody SendResetPasswordReq resetPasswordEmailReq) {
        SendResetPasswordRes response = memberService.sendResetPassword(resetPasswordEmailReq);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/reset-password/{uuid}")
    public ResponseEntity<String> resetPassword(@PathVariable("uuid") String uuid, @RequestBody String newPassword){
        memberService.resetPassword(uuid, newPassword);
        return ResponseEntity.ok("비밀번호가 성공적으로 재설정 되었습니다.");
    }

    @GetMapping("/users")
    public ResponseEntity<MemberResponseDTO> getUserInfo(){
       MemberResponseDTO member =  memberService.getMemberInfo();
       return ResponseEntity.ok(member);
    }

    @PutMapping("/users")
    public ResponseEntity<MemberResponseDTO> updateUserInfo(@RequestBody MemberUpdateRequestDTO memberUpdateRequestDTO){
        MemberResponseDTO updatedUser = memberService.updateMemberInfo(memberUpdateRequestDTO);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/users")
    public void deleteUserInfo(HttpSession session) {
        Long memberId = (Long) session.getAttribute("memberId");
        session.invalidate();
        memberService.deleteMember(memberId);
    }
}