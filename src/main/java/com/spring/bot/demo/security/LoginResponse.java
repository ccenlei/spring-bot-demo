package com.spring.bot.demo.security;

import java.util.Collection;
import java.util.Date;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoginResponse {

    private int status;

    private String message;

    private String path;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date timestamp;

    private String username;

    private Collection<GrantedAuthority> authorities;

    public static LoginResponse of(Authentication authentication) {
        LoginResponse response = new LoginResponse();
        response.setStatus(200);
        response.setTimestamp(new Date());
        User user = (User) authentication.getPrincipal();
        response.setUsername(user.getUsername());
        response.setAuthorities(user.getAuthorities());
        return response;
    }

    public LoginResponse message(String message) {
        this.message = message;
        return this;
    }

    public LoginResponse path(String path) {
        this.path = path;
        return this;
    }
}
