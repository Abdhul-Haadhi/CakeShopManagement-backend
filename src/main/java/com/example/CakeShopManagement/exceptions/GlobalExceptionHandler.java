package com.example.CakeShopManagement.exceptions;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(AppException.class)
    public ResponseEntity<Map<String, String>> handleAppException(AppException ex) {

        Map<String, String> error = new HashMap<>();
        error.put("message", ex.getMessage());

        return ResponseEntity
                .status(ex.getHttpStatus())
                .body(error);
    }
}
