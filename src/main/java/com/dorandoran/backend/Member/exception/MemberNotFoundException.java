package com.dorandoran.backend.Member.exception;

public class MemberNotFoundException extends RuntimeException {

    public MemberNotFoundException() {
        super("Member does not exist");
    }

    public MemberNotFoundException(String message) {
        super(message);
    }
}