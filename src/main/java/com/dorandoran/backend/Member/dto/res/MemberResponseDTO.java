package com.dorandoran.backend.Member.dto.res;

import com.dorandoran.backend.Member.domain.Address;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class MemberResponseDTO {
    private Long id;
    private String name;
    private String email;
    private Address address;

    public MemberResponseDTO(Long id, String name, String email, Address address) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.address = address;
    }
}
