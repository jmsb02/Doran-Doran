package com.dorandoran.backend.exception;

import com.dorandoran.backend.Club.exception.ClubNotFoundException;
import com.dorandoran.backend.Member.exception.InvalidUuidException;
import com.dorandoran.backend.Member.exception.MemberNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    // MemberNotFoundException 처리
    @ExceptionHandler(MemberNotFoundException.class)
    public ResponseEntity<String> handleMemberNotFoundException(MemberNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    // InvalidUuidException 처리
    @ExceptionHandler(InvalidUuidException.class)
    public ResponseEntity<String> handleInvalidUuidException(InvalidUuidException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    // ClubNotFoundException 처리
    @ExceptionHandler(ClubNotFoundException.class)
    public ResponseEntity<String> handleClubNotFoundException(ClubNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    // 기타 예외 처리
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }
}
