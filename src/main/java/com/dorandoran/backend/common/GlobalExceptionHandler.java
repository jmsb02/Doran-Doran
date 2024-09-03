package com.dorandoran.backend.common;

import com.dorandoran.backend.File.exception.CustomS3Exception;
import com.dorandoran.backend.File.exception.FileMissingException;
import com.dorandoran.backend.Member.exception.InvalidUuidException;
import com.dorandoran.backend.Member.exception.MemberNotFoundException;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.FileNotFoundException;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(MemberNotFoundException.class)
    public ResponseEntity<String> handleMemberNotFoundException(MemberNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(InvalidUuidException.class)
    public ResponseEntity<String> handleInvalidUuidException(InvalidUuidException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler(CustomS3Exception.class)
    public ResponseEntity<String> handleCustomS3Exception(CustomS3Exception ex) {
        log.error("CustomS3Exception 발생: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneralException(Exception ex) {
        log.error("예외 발생: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류");
    }

    @ExceptionHandler(FileMissingException.class)
    public ResponseEntity<String> handleFileNotFoundException(FileNotFoundException ex) {
        log.error("파일을 찾을 수 없습니다: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("파일을 찾을 수 없습니다.");
    }

    // 새로운 핸들러 추가
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> handleConstraintViolationException(ConstraintViolationException ex) {
        log.error("ConstraintViolationException 발생: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("유효성 검사 실패: " + ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult().getFieldError().getDefaultMessage();
        log.error("MethodArgumentNotValidException 발생: {}", errorMessage);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("유효성 검사 실패: " + errorMessage);
    }
}

