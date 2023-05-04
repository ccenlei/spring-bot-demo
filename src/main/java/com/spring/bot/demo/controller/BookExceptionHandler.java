package com.spring.bot.demo.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import com.spring.bot.demo.exception.BookException;
import com.spring.bot.demo.exception.ErrorCode;
import com.spring.bot.demo.exception.ErrorResponse;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@ControllerAdvice(assignableTypes = { BookController.class })
public class BookExceptionHandler {

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<?> handelArgumentException(MethodArgumentNotValidException ex, HttpServletRequest request) {
        Map<String, Object> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        ErrorResponse response = ErrorResponse.builder()
                .code(2000)
                .status(HttpStatus.BAD_REQUEST.value())
                .message("request validate failed")
                .path(request.getRequestURI())
                .timestamp(new Date())
                .data(errors)
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(value = BookException.class)
    public ResponseEntity<?> handelBookException(BookException ex, HttpServletRequest request) {
        ErrorCode code = ex.getErrorCode();
        ErrorResponse response = ErrorResponse.builder()
                .code(code.getCode())
                .status(code.getStatus().value())
                .message(code.getMessage())
                .path(request.getRequestURI())
                .timestamp(new Date())
                .data(ex.getData())
                .build();
        return ResponseEntity.status(code.getStatus()).body(response);
    }
}
