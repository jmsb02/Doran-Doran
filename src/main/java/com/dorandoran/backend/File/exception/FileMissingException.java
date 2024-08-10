package com.dorandoran.backend.File.exception;

public class FileMissingException extends RuntimeException{
    public FileMissingException() {
    }

    public FileMissingException(String message) {
        super(message);
    }
}
