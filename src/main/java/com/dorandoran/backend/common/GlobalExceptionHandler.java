package com.dorandoran.backend.common;

import com.dorandoran.backend.file.exception.CustomImageException;
import com.dorandoran.backend.file.exception.FileMissingException;
import com.dorandoran.backend.member.exception.DuplicateMemberException;
import com.dorandoran.backend.member.exception.InvalidUuidException;
import com.dorandoran.backend.member.exception.MemberNotFoundException;
import com.dorandoran.backend.post.exception.PostNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MultipartException;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 멤버 관련 Exception
     */
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<String> handleUserNotFound(UsernameNotFoundException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }

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

    @ExceptionHandler(DuplicateMemberException.class)
    public ResponseEntity<String> handleDuplicateMemberException(DuplicateMemberException ex) {
        log.error("DuplicateMemberException 발생: {}", ex.getMessage());
        StackTraceElement[] trace = ex.getStackTrace();
        for (StackTraceElement element : trace) {
            log.error("handleDuplicateMemberException message: {}}",element);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    /**
     * 파일(+마커) 관련 Exception
     */
    @ExceptionHandler(CustomImageException.class)
    public ResponseEntity<String> handleCustomS3Exception(CustomImageException ex) {
        log.error("CustomImageException 발생: {}", ex.getMessage());
        StackTraceElement[] trace = ex.getStackTrace();
        for (StackTraceElement element : trace) {
            log.error("handleCustomImageException message: {}}",element);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
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

    @ExceptionHandler(MultipartException.class)
    public ResponseEntity<String> handleMultipartException(MultipartException ex) {
        log.error("MultipartException 발생: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("파일 업로드 중 문제가 발생했습니다. 파일 크기나 형식을 확인하세요.");
    }

    /**
     * Post관련 Exception
     */
    @ExceptionHandler(PostNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handlePostNotFoundException(PostNotFoundException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", false);
        response.put("error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
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

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<String> handleMethodNotSupported(HttpRequestMethodNotSupportedException ex) {
        log.error("지원하지 않는 요청 메서드: {}", ex.getMethod());
        StackTraceElement[] trace = ex.getStackTrace();
        log.error("trace : {}", (Object) trace.clone());
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body("지원하지 않는 요청 메서드입니다.");
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneralException(Exception ex) {
        log.error("예외 발생: {}", ex.getMessage());
        StackTraceElement[] trace = ex.getStackTrace();
        log.error("trace : {}", (Object) trace.clone());
        for (StackTraceElement element : trace) {
            log.error("handleGeneralException message: {}}",element);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류");
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleGeneralRuntimeException(RuntimeException ex) {
        log.error("예외 발생: {}", ex.getMessage());
        StackTraceElement[] trace = ex.getStackTrace();
        log.error("trace : {}", (Object) trace.clone());
        for (StackTraceElement element : trace) {
            log.error("handleGeneralException message: {}}",element);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류");
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


}

