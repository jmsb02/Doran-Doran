package com.dorandoran.backend.member.dto.res;

import com.dorandoran.backend.member.domain.MemberAddress;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Schema(description = "회원 정보 응답 데이터")
public class MemberResponseDTO {

    @Schema(description = "회원 ID", example = "1")
    private Long id;

    @Schema(description = "회원 이름", example = "도란도란")
    private String name;

    @Schema(description = "회원 이메일", example = "member@naver.com")
    private String email;

    @Schema(description = "회원 주소 정보")
    private MemberAddress address;

    public MemberResponseDTO(Long id, String name, String email, MemberAddress address) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.address = address;
    }
}
