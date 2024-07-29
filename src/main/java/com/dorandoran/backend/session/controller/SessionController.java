package com.dorandoran.backend.session.controller;

import com.dorandoran.backend.Member.domain.Member;
import com.dorandoran.backend.Member.domain.MemberService;
import com.dorandoran.backend.Member.dto.JoinRequest;
import com.dorandoran.backend.Member.dto.LoginRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
@RequiredArgsConstructor
public class SessionController {
    private final MemberService memberService;

    @GetMapping(value = "/")
    public String home(Model model,@SessionAttribute(name = "memberId",required = false) Long memberId) {
       Member loginMember = memberService.getLoginMemberById(memberId);
       if(loginMember != null) {
           model.addAttribute("name",loginMember.getName());
       }
       return "login";
    }

    @GetMapping("/join")
    public String join(Model model){
        model.addAttribute("joinRequest",new JoinRequest());
        return "join";
    }

    @PostMapping("/join")
    public String join(@Valid @ModelAttribute("joinRequest") JoinRequest joinRequest, BindingResult bindingResult,Model model){
        //loginId 중복체크
        if(memberService.checkLoginIdDuplicate(joinRequest.getLonginId())){
            bindingResult.addError(new FieldError("joinRequest","loginId","로그인 아이디가 중복됩니다."));
        }

        //닉네임 중복 체크
        if(memberService.checkNameDuplicate(joinRequest.getName())){
            bindingResult.addError(new FieldError("joinRequest","name","닉네임이 중복됩니다."));
        }

        //password와 password 같은지 체크
        if(!joinRequest.getPassword().equals(joinRequest.getPasswordCheck())){
            bindingResult.addError(new FieldError("joinRequest","passwordCheck","비밀번호가 일치하지 않습니다"));
        }

        if(bindingResult.hasErrors()){
            return "join";
        }
        memberService.join(joinRequest);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login(Model model){
        model.addAttribute("loginRequest",new LoginRequest());
        return "login";
    }

    @PostMapping("/login")
    public String login(@Valid @ModelAttribute("loginRequest") LoginRequest loginRequest, BindingResult bindingResult, HttpServletRequest httpServletRequest,Model model){
       Member member =  memberService.login(loginRequest);
        // 로그인 아이디나 비밀번호가 틀린 경우 global error return
        if(member == null){
            bindingResult.reject("loginfail","로그인 아이디 또는 비밀번호가 틀렸습니다.");
        }

        if(bindingResult.hasErrors()){
            return "login";
        }

        //로그인 성공 시 세션 생성

        //세션 생성 전 기존의 세션 파기
        httpServletRequest.getSession().invalidate();
        HttpSession session = httpServletRequest.getSession(true);
        session.setAttribute("memberId",member.getId());
        session.setMaxInactiveInterval(1800);

        return "redirect:/";
    }
    @GetMapping("/logout")
    public String logout(HttpServletRequest httpServletRequest,Model model){
        HttpSession session = httpServletRequest.getSession(false);
        if(session != null){
            session.invalidate();
        }
        return "redirect:/";
    }

}
