package com.spring.bot.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.extern.slf4j.Slf4j;

@Validated
@RestController
@RequestMapping("/api/roles")
@Slf4j
public class RoleController {

    @Autowired
    private UserDetailsService userDetailsService;

    @GetMapping("/admin")
    public ResponseEntity<UserDetails> userByName(@RequestParam("username") @Valid @NotBlank String name) {
        UserDetails uDetails = null;
        try {
            uDetails = userDetailsService.loadUserByUsername(name);
        } catch (UsernameNotFoundException e) {
            log.warn("no user by : {}", name);
        }
        if (uDetails == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(uDetails);
    }

    @GetMapping("/user")
    public ResponseEntity<UserDetails> user(@Autowired Authentication authentication) {
        UserDetails uDetails = (UserDetails) authentication.getPrincipal();
        return ResponseEntity.ok().body(uDetails);
    }
}
