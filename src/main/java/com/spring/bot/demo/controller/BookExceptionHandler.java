package com.spring.bot.demo.controller;

import java.time.Instant;

import org.springframework.http.ResponseEntity;
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

    @ExceptionHandler(value = BookException.class)
    public ResponseEntity<?> handelBookException(BookException ex, HttpServletRequest request) {
        ErrorCode code = ex.getErrorCode();
        ErrorResponse response = ErrorResponse.builder()
                .code(code.getCode())
                .status(code.getStatus().value())
                .msessage(code.getMessage())
                .path(request.getRequestURI())
                .timestamp(Instant.now())
                .data(ex.getData())
                .build();
        return ResponseEntity.status(code.getStatus()).body(response);
    }
}
