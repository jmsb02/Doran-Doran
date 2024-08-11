package com.dorandoran.backend.Member.Controller;

import com.dorandoran.backend.Member.domain.MemberRepository;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
@Slf4j
class MemberControllerTest {
    @Autowired
    MemberController memberController;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    WebApplicationContext webApplicationContext;

    private HttpSession session;

    @Test
    void home() {

    }

    @Test
    void signup() {

    }

    @Test
    void login() {
    }

    @Test
    void logout() {
    }

    @Test
    void findLoginId() {
    }

    @Test
    void sendResetPassword() {
    }

    @Test
    void resetPassword() {
    }

    @Test
    void getUserInfo() {
    }

    @Test
    void updateUserInfo() {
    }

    @Test
    void deleteUserInfo() {
    }
}