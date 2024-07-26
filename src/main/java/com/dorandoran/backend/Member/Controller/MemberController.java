package com.dorandoran.backend.Member.Controller;

import com.dorandoran.backend.Member.Model.MemberService;
import com.dorandoran.backend.Member.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/")
    public String home(Model model) {
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!email.equals("anonymousUser")) {
            MemberDto member = (MemberDto) memberService.loadUserByUsername(email);
            member.setPassword(null);
            model.addAttribute("member", member);
        }
        return "home";
    }

    @GetMapping("/login")
    public String login(Model model,
                        @RequestParam(value="error", required = false) String error,
                        @RequestParam(value = "exception", required = false) String exception) {
        model.addAttribute("error", error);
        model.addAttribute("exception", exception);
        return "login";
    }

    @GetMapping("/logout")
    public String logout() {
        SecurityContextHolder.clearContext();
        return "redirect:/login?logout";
    }
}