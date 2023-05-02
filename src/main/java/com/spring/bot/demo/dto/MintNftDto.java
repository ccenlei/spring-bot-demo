package com.spring.bot.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MintNftDto {

    private Integer id;

    @NotNull(message = "name can't be null")
    private String name;

    @NotNull(message = "token id can't be null")
    private String tokenId;

    @NotBlank(message = "addr can't be blank")
    private String addr;

    private String creator;

    private Integer total;

    private Integer ownerId;
}
