package com.dorandoran.backend.Member.Controller;

import com.dorandoran.backend.Member.Model.MemberRepository;
import com.dorandoran.backend.Member.Model.MemberService;
import com.dorandoran.backend.Member.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final MemberRepository memberRepository;

    @GetMapping("/")
    public String home(Model model) {
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!email.equals("anonymousUser")) {
            MemberDto user = (MemberDto) memberService.loadUserByUsername(email);
            user.setPassword(null);
            model.addAttribute("user", user);
        }
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}