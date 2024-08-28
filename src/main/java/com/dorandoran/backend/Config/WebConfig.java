//package com.dorandoran.backend.Config;
//
//import com.dorandoran.backend.Member.domain.AuthInterceptor;
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//@Configuration
//@RequiredArgsConstructor
//public class WebConfig implements WebMvcConfigurer {
//    private final AuthInterceptor authInterceptor;
//
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(authInterceptor)
//                .addPathPatterns("/**")  // 모든 경로에 대해 인터셉터 적용
//                .excludePathPatterns("/login", "/signUp", "/reset-password/**","/");
//    }
//}
