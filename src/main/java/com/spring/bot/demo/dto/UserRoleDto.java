package com.spring.bot.demo.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserRoleDto {

    @NotBlank(message = "username can't be blank")
    private String username;

    @NotBlank(message = "password can't be blank")
    private String password;

    private String role;
}
