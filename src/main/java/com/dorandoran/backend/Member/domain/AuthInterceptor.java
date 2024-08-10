package com.dorandoran.backend.Member.domain;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession(false);

        // 세션이 없거나, 세션에 memberId가 없으면 로그인 페이지로 리다이렉트
        if (session == null || session.getAttribute("memberId") == null) {
            response.sendRedirect("/members/login");
            return false;
        }
        // 로그인된 사용자라면 요청을 계속 처리
        return true;
    }
}
