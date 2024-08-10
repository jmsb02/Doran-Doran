package com.dorandoran.backend.Member.exception;

public class MemberNotFoundException extends RuntimeException {

    public MemberNotFoundException() {
        super("존재하지 않는 회원입니다.");
    }

    public MemberNotFoundException(String message) {
        super(message);
    }
}