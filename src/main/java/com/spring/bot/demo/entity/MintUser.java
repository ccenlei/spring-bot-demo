package com.spring.bot.demo.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MintUser {

    private Integer id;

    private String name;

    private String addr;

    private Integer following;

    private Integer followers;

    private List<MintNft> mNfts;
}
