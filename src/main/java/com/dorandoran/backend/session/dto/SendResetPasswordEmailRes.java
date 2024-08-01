package com.dorandoran.backend.session.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SendResetPasswordEmailRes {
    private String UUID;
}
