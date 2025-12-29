package com.yusufziyrek.clean_architecture_training.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.validation.ConstraintViolationException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Global Exception Handler - Tüm controller hatalarını yakalar.
 * 
 * Clean Architecture Prensibi: Bu sınıf SADECE common paketindeki
 * BaseException'a bağımlıdır. Modül-specific exception'ları bilmez.
 * Bu sayede yeni modül veya exception eklendiğinde bu dosya değişmez.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * TÜM Domain Exception'ları yakalar (BaseException'dan türeyen).
     * HTTP status, exception code'una göre belirlenir.
     */
    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ErrorResponse> handleDomainException(BaseException ex) {
        HttpStatus status = determineHttpStatus(ex.getCode());

        ErrorResponse error = new ErrorResponse(
                ex.getMessage(),
                ex.getCode(),
                LocalDateTime.now());

        return new ResponseEntity<>(error, status);
    }

    /**
     * Exception code'una göre uygun HTTP status döndürür.
     * Yeni exception eklendiğinde sadece naming convention'a uyulmalı.
     */
    private HttpStatus determineHttpStatus(String code) {
        if (code == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }

        // NOT_FOUND içeren kodlar için 404
        if (code.contains("NOT_FOUND")) {
            return HttpStatus.NOT_FOUND;
        }

        // INVALID, INSUFFICIENT gibi iş kuralı ihlalleri için 400
        if (code.contains("INVALID") || code.contains("INSUFFICIENT")) {
            return HttpStatus.BAD_REQUEST;
        }

        // Varsayılan: 400 Bad Request
        return HttpStatus.BAD_REQUEST;
    }

    /**
     * Bean Validation hatalarını yakalar (@Valid anotasyonu).
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> validationErrors = new HashMap<>();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            validationErrors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }

        ErrorResponse error = new ErrorResponse(
                "Validation error: " + validationErrors,
                "VALIDATION_ERROR",
                LocalDateTime.now());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    /**
     * PathVariable/RequestParam validasyon hatalarını yakalar.
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolationException(
            ConstraintViolationException ex) {
        ErrorResponse error = new ErrorResponse(
                "Validation error: " + ex.getMessage(),
                "VALIDATION_ERROR",
                LocalDateTime.now());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    /**
     * Beklenmeyen tüm hataları yakalar (fallback).
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneralException(Exception ex) {
        ErrorResponse error = new ErrorResponse(
                "An unexpected error occurred: " + ex.getMessage(),
                "INTERNAL_SERVER_ERROR",
                LocalDateTime.now());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
