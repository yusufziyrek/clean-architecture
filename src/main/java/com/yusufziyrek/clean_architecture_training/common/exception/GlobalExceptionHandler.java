package com.yusufziyrek.clean_architecture_training.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.yusufziyrek.clean_architecture_training.modules.order.domain.InvalidOrderQuantityException;
import com.yusufziyrek.clean_architecture_training.modules.product.domain.InsufficientStockException;
import com.yusufziyrek.clean_architecture_training.modules.product.domain.ProductNotFoundException;

import java.time.LocalDateTime;

@RestControllerAdvice // Tüm controller hatalarını dinler
public class GlobalExceptionHandler {

    // Stok yetersiz hatasını 400 Bad Request'e çevir
    @ExceptionHandler(InsufficientStockException.class)
    public ResponseEntity<ErrorResponse> handleInsufficientStock(InsufficientStockException ex) {
        ErrorResponse error = new ErrorResponse(
                ex.getMessage(),
                ex.getCode(),
                LocalDateTime.now());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    // Sipariş adedi hatalı hatayı 400 Bad Request'e çevir
    @ExceptionHandler(InvalidOrderQuantityException.class)
    public ResponseEntity<ErrorResponse> handleInvalidOrderQuantity(InvalidOrderQuantityException ex) {
        ErrorResponse error = new ErrorResponse(
                ex.getMessage(),
                ex.getCode(),
                LocalDateTime.now());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    // Ürün bulunamadı hatasını 400 Bad Request'e çevir
    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleProductNotFoundException(ProductNotFoundException ex) {
        ErrorResponse error = new ErrorResponse(
                ex.getMessage(),
                ex.getCode(),
                LocalDateTime.now());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    // Bilinmeyen tüm hataları 500 Internal Server Error'a çevir
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneralException(Exception ex) {
        ErrorResponse error = new ErrorResponse(
                "Beklenmedik bir hata oluştu.",
                "INTERNAL_SERVER_ERROR",
                LocalDateTime.now());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}