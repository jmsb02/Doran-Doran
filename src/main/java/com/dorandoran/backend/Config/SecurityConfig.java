package com.dorandoran.backend.Config;

import com.dorandoran.backend.Member.domain.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;



@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;

    /**
     * 애플리케이션 보안 정책 정의
     * 어떤 URL이 인증 필요한지, 필요하지 않는지, 로그인, 로그아웃 기능
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                /**
                 *  Spring Security가 세션을 관리하고 간단한 사이드 프로젝트에서 회원 인증이 필요 없는 경우,
                 *  CSRF 보호를 비활성화해도 안전함
                 */
                .csrf(csrf -> csrf.disable()) // CSRF 보호 비활성화 (테스트용)

                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/api/members/signup", "/api/members/login", "/api/members/**").permitAll() //인증 x
                        .anyRequest().authenticated() // 나머지 모든 요청은 인증 필요
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED) // 세션 관리 정책 설정
                );

        return http.build();
    }

    /**
     * 사용자 인증 처리하는 AuthenticationManager 설정
     * 사용자가 로그인할 때 제공하는 인증 정보 검증
     */
    @Bean
    public AuthenticationManager authManger(HttpSecurity http) throws Exception {

        //AuthenticationManagerBuilder를 통해 사용자 세부 정보 서비스와 비밀번호 인코더 설정
        //http.getSharedObject를 통해 Spring Security의 내부 설정을 공유하여 사용
        AuthenticationManagerBuilder authenticationManagerBuilder
                = http.getSharedObject(AuthenticationManagerBuilder.class);

        //userDetailsService를 통해 사용자 정보 가져와 인증 수행 + passwordEncoder를 통한 암호화 진행
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
        return authenticationManagerBuilder.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

