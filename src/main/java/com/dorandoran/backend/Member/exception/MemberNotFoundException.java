package com.dorandoran.backend.Member.exception;

public class MemberNotFoundException extends RuntimeException {

    public MemberNotFoundException() {
        super("멤버가 존재하지 않습니다.");
    }
}
