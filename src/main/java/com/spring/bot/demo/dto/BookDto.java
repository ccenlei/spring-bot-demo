package com.spring.bot.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BookDto {

    private Integer id;

    @NotBlank(message = "name can' be blank")
    private String name;

    @NotNull(message = "des can't be null")
    private String des;
}
