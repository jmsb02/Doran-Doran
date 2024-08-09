package com.dorandoran.backend.Member.dto.res;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SendResetPasswordRes {
    private String resetPasswordLink;
}
