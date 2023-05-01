package com.spring.bot.demo.controller;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.spring.bot.demo.exception.ErrorCode;
import com.spring.bot.demo.exception.ErrorResponse;
import com.spring.bot.demo.exception.MintFunException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;

@RestController
@ControllerAdvice(assignableTypes = MintFunController.class)
public class MintFunExceptionHandler {

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<?> handelArgumentException(MethodArgumentNotValidException ex, HttpServletRequest request) {
        Map<String, Object> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        ErrorResponse response = ErrorResponse.builder()
                .code(3000)
                .status(HttpStatus.BAD_REQUEST.value())
                .message("request validate failed")
                .path(request.getRequestURI())
                .timestamp(Instant.now())
                .data(errors)
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(value = { ConstraintViolationException.class, MethodArgumentTypeMismatchException.class,
            MissingServletRequestParameterException.class })
    public ResponseEntity<?> handelArgumentException(Exception ex, HttpServletRequest request) {
        Map<String, Object> errors = new HashMap<>();
        errors.put("path variable error", ex.getMessage());
        ErrorResponse response = ErrorResponse.builder()
                .code(3000)
                .status(HttpStatus.BAD_REQUEST.value())
                .message("request validate failed")
                .path(request.getRequestURI())
                .timestamp(Instant.now())
                .data(errors)
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(value = MintFunException.class)
    public ResponseEntity<?> handelMintFunException(MintFunException ex, HttpServletRequest request) {
        ErrorCode code = ex.getErrorCode();
        ErrorResponse response = ErrorResponse.builder()
                .code(code.getCode())
                .status(code.getStatus().value())
                .message(code.getMessage())
                .path(request.getRequestURI())
                .timestamp(Instant.now())
                .data(ex.getData())
                .build();
        return ResponseEntity.status(code.getStatus()).body(response);
    }
}
