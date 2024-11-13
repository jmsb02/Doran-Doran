package com.dorandoran.backend.Config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @Info(
        title = "Marker API",
        version = "1.0",
        description = "도란도란 관련 API",
        contact = @Contact(name = "이재민", email = "jmsb02@naver.com")
))
public class SwaggerConfig {
}
