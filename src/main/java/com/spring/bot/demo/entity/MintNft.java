package com.spring.bot.demo.entity;

import lombok.Data;

@Data
public class MintNft {

    private Integer id;

    private String name;

    private String tokenId;

    private String addr;

    private String creator;

    private Integer total;

    private Integer ownerId;
}
