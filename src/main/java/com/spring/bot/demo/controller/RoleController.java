package com.spring.bot.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spring.bot.demo.dto.UserRoleDto;
import com.spring.bot.demo.utils.DemoUtils;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.extern.slf4j.Slf4j;

@Validated
@RestController
@RequestMapping("/api/roles")
@Slf4j
public class RoleController {

    @Autowired
    private JdbcUserDetailsManager userDetailsService;

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

    @PostMapping("/admin/addUser")
    public ResponseEntity<?> addUser(@RequestBody @Valid UserRoleDto uRoleDto) {
        if (uRoleDto.getRole() == null) {
            uRoleDto.setRole("USER");
        }
        if (!userDetailsService.userExists(uRoleDto.getUsername())) {
            userDetailsService.createUser(User.withDefaultPasswordEncoder()
                    .username(uRoleDto.getUsername()).password(uRoleDto.getPassword())
                    .roles(DemoUtils.roles(uRoleDto.getRole()))
                    .build());
        }
        return ResponseEntity.accepted().build();
    }

    @GetMapping("/user")
    public ResponseEntity<UserDetails> user(@Autowired Authentication authentication) {
        UserDetails uDetails = (UserDetails) authentication.getPrincipal();
        return ResponseEntity.ok().body(uDetails);
    }
}
