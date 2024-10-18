package com.dorandoran.backend.common;

import com.dorandoran.backend.File.exception.CustomImageException;
import com.dorandoran.backend.File.exception.FileMissingException;
import com.dorandoran.backend.Member.exception.DuplicateMemberException;
import com.dorandoran.backend.Member.exception.InvalidUuidException;
import com.dorandoran.backend.Member.exception.MemberNotFoundException;
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
        StackTraceElement[] trace = e.getStackTrace();
        for (StackTraceElement element : trace) {
            log.error("MemberNotFound message: {}}",element);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(InvalidUuidException.class)
    public ResponseEntity<String> handleInvalidUuidException(InvalidUuidException e) {
        StackTraceElement[] trace = e.getStackTrace();
        for (StackTraceElement element : trace) {
            log.error("handleInvalidUuidException message: {}}",element);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler(CustomImageException.class)
    public ResponseEntity<String> handleCustomS3Exception(CustomImageException ex) {
        log.error("CustomImageException 발생: {}", ex.getMessage());
        StackTraceElement[] trace = ex.getStackTrace();
        for (StackTraceElement element : trace) {
            log.error("handleCustomImageException message: {}}",element);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneralException(Exception ex) {
        log.error("예외 발생: {}", ex.getMessage());
        StackTraceElement[] trace = ex.getStackTrace();
        for (StackTraceElement element : trace) {
            log.error("handleGeneralException message: {}}",element);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류");
    }

    @ExceptionHandler(FileMissingException.class)
    public ResponseEntity<String> handleFileNotFoundException(FileNotFoundException ex) {
        log.error("파일을 찾을 수 없습니다: {}", ex.getMessage());
        StackTraceElement[] trace = ex.getStackTrace();
        for (StackTraceElement element : trace) {
            log.error("handleFileNotFoundException message: {}}",element);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("파일을 찾을 수 없습니다.");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult().getFieldError().getDefaultMessage();
        log.error("MethodArgumentNotValidException 발생: {}", errorMessage);
        StackTraceElement[] trace = ex.getStackTrace();
        for (StackTraceElement element : trace) {
            log.error("MethodArgumentNotValidException message: {}}",element);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("유효성 검사 실패: " + errorMessage);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        log.error("IllegalArgumentException 발생: {}", ex.getMessage());
        log.error("handleIllegalArgumentException 발생: {}", ex.getMessage());
        StackTraceElement[] trace = ex.getStackTrace();
        for (StackTraceElement element : trace) {
            log.error("MethodArgumentNotValidException message: {}}",element);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(DuplicateMemberException.class)
    public ResponseEntity<String> handleDuplicateMemberException(DuplicateMemberException ex) {
        log.error("DuplicateMemberException 발생: {}", ex.getMessage());
        StackTraceElement[] trace = ex.getStackTrace();
        for (StackTraceElement element : trace) {
            log.error("handleDuplicateMemberException message: {}}",element);
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }
}

