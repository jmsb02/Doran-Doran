package com.dorandoran.backend.Member.dto.req;

import lombok.Getter;

@Getter
public class SendResetPasswordReq {
    private String loginId;
    private String email;
}
