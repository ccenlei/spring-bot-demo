package com.spring.bot.demo.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FileDemoDto {

    private String name;

    private String downloadUrl;

    private String previewUrl;
}
