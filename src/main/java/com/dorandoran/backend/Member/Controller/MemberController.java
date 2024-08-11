package com.dorandoran.backend.Member.Controller;

import com.dorandoran.backend.Member.domain.MemberService;
import com.dorandoran.backend.Member.dto.req.LoginRequest;
import com.dorandoran.backend.Member.dto.req.MemberUpdateRequestDTO;
import com.dorandoran.backend.Member.dto.req.SendResetPasswordReq;
import com.dorandoran.backend.Member.dto.req.SignUpRequest;
import com.dorandoran.backend.Member.dto.res.MemberResponseDTO;
import com.dorandoran.backend.Member.dto.res.SendResetPasswordRes;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<String> login(@Valid @RequestBody LoginRequest loginRequest) {
        memberService.login(loginRequest);
        return ResponseEntity.ok("로그인에 성공하였습니다.");
    }

    @GetMapping("/logout")
    public void logout(HttpSession session) {
        session.invalidate();
    }


    @GetMapping("/find-id")
    public ResponseEntity<String> findLoginId() {
        //현재 인증된 사요자의 인증 객체 가져옴
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //서비스계층으로 인증객체를 전달하여 로그인아이디 조회
        String result = memberService.findLoginIdByEmail(authentication);
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