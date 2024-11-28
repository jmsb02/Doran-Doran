package com.dorandoran.backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {

        // 모든 요청에 대해 CORS 허용
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000") // React 앱의 출처
                .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH") // 허용할 HTTP 메소드
                .allowCredentials(true);
    }

}
