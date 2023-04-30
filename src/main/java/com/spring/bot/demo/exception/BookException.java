package com.spring.bot.demo.exception;

import java.util.Map;

public class BookException extends BaseException {

    public BookException(Map<String, Object> data) {
        super(ErrorCode.BOOK_NOT_FOUND, data);
    }
}
