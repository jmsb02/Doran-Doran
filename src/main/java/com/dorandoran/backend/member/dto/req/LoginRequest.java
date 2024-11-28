package com.dorandoran.backend.member.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "로그인 요청에 필요한 정보")
public class LoginRequest {

    @NotBlank(message = "아이디를 입력해주세요.")
    @Schema(description = "회원 로그인 ID", example = "user123")
    private String loginId;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Schema(description = "회원 비밀번호", example = "password123!")
    private String password;
}
