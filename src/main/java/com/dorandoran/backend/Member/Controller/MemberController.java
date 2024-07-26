package com.dorandoran.backend.Member.Controller;

import com.dorandoran.backend.Member.Model.MemberCommendService;
import com.dorandoran.backend.Member.dto.MemberDto;
import com.dorandoran.backend.Member.Model.CustomMemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final CustomMemberService customMemberService;
    private final MemberCommendService memberCommendService;

    @GetMapping("/")
    public String home(Model model){
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.info("Home accessed, principal: " + email);
        if(!(email.equals("anonymousUser"))) {
            log.info("Authenticated user: " + email);
            MemberDto user = (MemberDto) customMemberService.loadUserByUsername(email);
            user.setPassword(null);
            model.addAttribute("user", user);
        }
        return "index";
    }

    @GetMapping("/login")
    public String login(Model model,
                        @RequestParam(value="error", required = false) String error,
                        @RequestParam(value = "exception", required = false) String exception){
        log.info("Login page accessed, error: " + error + ", exception: " + exception);
        model.addAttribute("error", error);
        model.addAttribute("exception", exception);
        return "login";
    }
}