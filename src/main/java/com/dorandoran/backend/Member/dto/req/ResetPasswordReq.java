package com.dorandoran.backend.Member.dto.req;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ResetPasswordReq {
    private String newPassword;
}
