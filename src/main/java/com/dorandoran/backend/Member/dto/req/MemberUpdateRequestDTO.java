package com.dorandoran.backend.Member.dto.req;

import com.dorandoran.backend.Member.domain.Address;
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
    private Address address;
}
