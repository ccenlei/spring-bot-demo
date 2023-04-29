package com.spring.bot.demo.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Book {

    private Integer id;

    private String name;

    private String des;
}
