package com.dorandoran.backend.Member.dto;

import com.dorandoran.backend.Member.domain.Member;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class JoinRequest {
    @NotBlank(message = "닉네임을 입력해주세요.")
    private String name;

    @NotBlank(message = "로그인 아이디를 입력해주세요.")
    private String loginId;

    @NotBlank(message = "비밀번호가 비어있습니다.")
    private String password;
    private String passwordCheck;

    @NotBlank(message = "이메일을 입력해주세요.")
    private String email;


    //비밀번호 암호화
    public Member toEntity(String encodedPassword){
        return Member.builder()
                .loginId(this.loginId)
                .password(encodedPassword)
                .name(this.name)
                .email(this.email).build();
    }

}
