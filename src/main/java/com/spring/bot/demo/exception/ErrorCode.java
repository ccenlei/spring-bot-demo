package com.spring.bot.demo.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public enum ErrorCode {

    RESOURCE_NOT_FOUND(1001, HttpStatus.NOT_FOUND, "no such resource foud"),
    REQUEST_VALIDATION_FAILED(1002, HttpStatus.BAD_REQUEST, "request validate failed."),

    BOOK_NOT_FOUND(2001, HttpStatus.NOT_FOUND, "book not found"),

    MINT_USER_NOT_FOUND(3101, HttpStatus.NOT_FOUND, "mint fun user not found"),
    MINT_USER_DETAIL_NOT_FOUND(3102, HttpStatus.NOT_FOUND, "mint fun user detail not found"),
    MINT_NFT_NOT_FOUND(3201, HttpStatus.NOT_FOUND, "mint fun nft not found");

    private final int code;

    private final HttpStatus status;

    private final String message;

    ErrorCode(int code, HttpStatus status, String message) {
        this.code = code;
        this.status = status;
        this.message = message;
    }
}
