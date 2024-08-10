package com.dorandoran.backend.Member.exception;

public class InvalidUuidException extends RuntimeException {
    public InvalidUuidException() {
        super("유효하지 않은 UUID입니다.");
    }
}
