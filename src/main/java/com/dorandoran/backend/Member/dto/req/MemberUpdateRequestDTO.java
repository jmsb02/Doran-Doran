package com.dorandoran.backend.Member.dto.req;

import com.dorandoran.backend.Member.domain.Address;
import lombok.Getter;

@Getter
public class MemberUpdateRequestDTO {
    private String name;
    private String email;
    private String password;
    private String passwordCheck;
    private Address address;
}
