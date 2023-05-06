package com.spring.bot.demo.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FileResDto {

    private Integer status;

    private String message;
}
