package com.dorandoran.backend.Member.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class MemberDto {

    @NotBlank(message = "이메일은 필수 입력 항목입니다.")
    @Email(message = "유효한 이메일 주소를 입력하세요.")
    private String email;
}
