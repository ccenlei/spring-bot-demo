package com.spring.bot.demo.dto;

import lombok.Data;

@Data
public class MintNftDto {

    private Integer id;

    private String name;

    private String tokenId;

    private String addr;

    private String creator;

    private Integer total;

    private Integer ownerId;
}
