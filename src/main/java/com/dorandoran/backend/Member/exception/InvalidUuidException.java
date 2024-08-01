package com.dorandoran.backend.Member.exception;

public class InvalidUuidException extends RuntimeException {
    public InvalidUuidException() {
        super("Invalid UUID");
    }
}
