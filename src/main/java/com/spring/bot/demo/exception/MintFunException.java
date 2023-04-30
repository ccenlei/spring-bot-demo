package com.spring.bot.demo.exception;

import java.util.Map;

public class MintFunException extends BaseException {

    public MintFunException(Map<String, Object> data, ErrorCode code) {
        super(code, data);
    }
}
