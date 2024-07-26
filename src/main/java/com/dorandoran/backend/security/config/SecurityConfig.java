package com.dorandoran.backend.security.config;

import com.dorandoran.backend.Member.Model.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final MemberService customMemberService;

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.csrf().disable();

        // 권한에 따라 허용하는 URL 설정
        http.authorizeHttpRequests()
                .requestMatchers("/", "/login", "/signup", "/css/**", "/js/**", "/images/**").permitAll() // 정적 리소스 허용
                .anyRequest().authenticated(); // 나머지 요청들은 권한의 종류에 상관 없이 권한이 있어야만 접근 가능

        // login 설정
        http.formLogin()
                .loginPage("/login") // GET 요청 (login form을 보여줌)
                .loginProcessingUrl("/login") // POST 요청 (login 창에 입력한 데이터를 처리)
                .usernameParameter("email") // login에 필요한 id 값을 email로 설정 (default는 username)
                .passwordParameter("password") // login에 필요한 password 값을 password
                .defaultSuccessUrl("/") // login에 성공하면 /로 redirect
                .permitAll();

        // logout 설정
        http.logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
                .permitAll();

        return http.build();
    }
}