package com.spring.bot.demo.entity;

import lombok.Data;

@Data
public class MintUser {

    private Integer id;

    private String name;

    private String addr;

    private Integer following;

    private Integer followers;
}
