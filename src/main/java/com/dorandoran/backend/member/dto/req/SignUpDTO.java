package com.dorandoran.backend.member.dto.req;

import com.dorandoran.backend.member.domain.MemberAddress;
import com.dorandoran.backend.member.domain.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "회원 가입에 필요한 정보")
public class SignUpDTO {

    @Schema(description = "회원 이름", example = "도란도란")
    private String name;

    @NotBlank(message = "아이디는 영문과 숫자를 혼합해주세요.")
    @Schema(description = "회원 로그인 ID", example = "member123")
    private String loginId;

    @NotBlank(message = "패스워드는 알파벳 대소문자(a~z, A~Z), 숫자(0~9),특수문자로 구성해주세요.")
    @Schema(description = "회원 비밀번호", example = "password123!")
    private String password;

    @NotBlank(message = "이메일 형식에 맞춰 입력해주세요")
    @Email(message = "이메일 형식이 올바르지 않습니다.")
    @Schema(description = "회원 이메일", example = "member123@naver.com")
    private String email;

    @Schema(description = "회원 주소 정보")
    private MemberAddress address;

    @Column(nullable = true)
    @Schema(description = "프로필 이미지 URL", example = "/images/profile.jpg")
    private String profileImg;

    public Member toEntity() {
        return Member.builder()
                .name(this.name)
                .loginId(this.loginId)
                .password(this.password)
                .email(this.email)
                .address(this.address)
                .profileImg(this.profileImg)
                .build();
    }

}
