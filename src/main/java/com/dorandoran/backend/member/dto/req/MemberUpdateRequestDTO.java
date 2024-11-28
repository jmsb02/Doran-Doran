package com.dorandoran.backend.member.dto.req;

import com.dorandoran.backend.member.domain.MemberAddress;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberUpdateRequestDTO {

    private String name;
    private String email;
    private String password;
    private MemberAddress address;
}
