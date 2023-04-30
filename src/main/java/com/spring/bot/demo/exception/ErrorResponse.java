package com.spring.bot.demo.exception;

import java.time.Instant;
import java.util.Map;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * the unit response handles all project's exception
 */
@Getter
@Setter
@Builder
@ToString
public class ErrorResponse {

    private int code;

    private int status;

    private String message;

    private String path;

    private Instant timestamp;

    private Map<String, Object> data;
}
