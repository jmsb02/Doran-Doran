package com.dorandoran.backend.session.dto;

import com.dorandoran.backend.Member.domain.Address;
import com.dorandoran.backend.Member.domain.Member;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class JoinRequest {
    @NotBlank(message = "닉네임")
    private String name;

    @NotBlank(message = "아이디는 영문과 숫자를 혼합해주세요.")
    private String loginId;

    @NotBlank(message = "패스워드는 알파벳 대소문자(a~z, A~Z), 숫자(0~9),특수문자로 구성해주세요.")
    private String password;
    private String passwordCheck;

    @NotBlank(message = "이메일 형식에 맞춰 입력해주세요")
    @Email(message = "이메일 형식이 올바르지 않습니다.")
    private String email;

    private Address address;

    public Member toEntity(String encodedPassword){

        return Member.builder()
                .loginId(this.loginId)
                .password(encodedPassword)
                .name(this.name)
                .email(this.email)
                .address(address)
                .build();
    }

}
