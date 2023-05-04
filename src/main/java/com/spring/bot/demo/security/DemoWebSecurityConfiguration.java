package com.spring.bot.demo.security;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.bot.demo.exception.ErrorCode;
import com.spring.bot.demo.exception.ErrorResponse;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

/**
 * Define the rule of spring security.
 * 
 * 3 important beas:
 * 
 * @WebSecurityCustomizer
 * 
 * @SecurityFilterChain
 * 
 * @UserDetailsService
 */
@Configuration
@EnableWebSecurity
@Slf4j
public class DemoWebSecurityConfiguration {

    private final ObjectMapper oMapper = new ObjectMapper();

    /**
     * request validation ignores static files. (e.g. html/css)
     * 
     * @return
     */
    @Bean
    WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers("/static/**");
    }

    /**
     * the implementation of security.
     * 
     * requestMatchers: add role.
     * 
     * authenticated: all users need authenticated.
     * 
     * @param http
     * @return
     * @throws Exception
     */
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests()
                .requestMatchers("/api/roles/admin/**").hasRole("ADMIN")
                .requestMatchers("/api/roles/user/**").hasRole("USER")
                .anyRequest().authenticated()
                .and()
                .formLogin(login -> login.loginPage("/login.html").loginProcessingUrl("/dologin")
                        .successHandler((requset, response, authentication) -> {
                            LoginResponse lResponse = LoginResponse.of(authentication)
                                    .message("user login success")
                                    .path(requset.getRequestURI());
                            writeOut(lResponse, response);
                        })
                        .failureHandler((request, response, exp) -> {
                            ErrorCode errorCode;
                            if (exp instanceof LockedException) {
                                errorCode = ErrorCode.LGOIN_ACCOUNT_ACC_LOCKED;
                            } else if (exp instanceof CredentialsExpiredException) {
                                errorCode = ErrorCode.LGOIN_ACCOUNT_PWD_EXPIRED;
                            } else if (exp instanceof AccountExpiredException) {
                                errorCode = ErrorCode.LGOIN_ACCOUNT_ACC_EXPIRED;
                            } else if (exp instanceof DisabledException) {
                                errorCode = ErrorCode.LGOIN_ACCOUNT_ACC_DISABLE;
                            } else if (exp instanceof BadCredentialsException) {
                                errorCode = ErrorCode.LGOIN_ACCOUNT_NOT_FOUND;
                            } else {
                                log.error("uncatched exception :", exp);
                                throw exp;
                            }
                            ErrorResponse eResponse = ErrorResponse.builder()
                                    .timestamp(new Date()).path(request.getRequestURI())
                                    .code(errorCode.getCode())
                                    .status(errorCode.getStatus().value())
                                    .message(errorCode.getMessage())
                                    .build();
                            writeOut(eResponse, response);
                        })
                        .permitAll())
                .logout(logout -> logout
                        .logoutSuccessHandler((request, response, authentication) -> {
                            if (ObjectUtils.isNotEmpty(authentication)) {
                                LoginResponse lResponse = LoginResponse.of(authentication)
                                        .message("user logout success")
                                        .path(request.getRequestURI());
                                writeOut(lResponse, response);
                            } else {
                                ErrorResponse eResponse = ErrorResponse.builder()
                                        .timestamp(new Date()).path(request.getRequestURI())
                                        .code(ErrorCode.LGOIN_ACCOUNT_NOT_YET.getCode())
                                        .status(ErrorCode.LGOIN_ACCOUNT_NOT_YET.getStatus().value())
                                        .message(ErrorCode.LGOIN_ACCOUNT_NOT_YET.getMessage())
                                        .build();
                                writeOut(eResponse, response);
                            }
                        }))
                .csrf(csrf -> csrf.disable())
                .exceptionHandling(handling -> handling
                        .authenticationEntryPoint((request, response, exp) -> {
                            System.out.println(exp);
                            ErrorResponse eResponse = ErrorResponse.builder()
                                    .timestamp(new Date()).path(request.getRequestURI())
                                    .code(ErrorCode.LGOIN_ACCOUNT_NOT_YET.getCode())
                                    .status(ErrorCode.LGOIN_ACCOUNT_NOT_YET.getStatus().value())
                                    .message(ErrorCode.LGOIN_ACCOUNT_NOT_YET.getMessage())
                                    .build();
                            writeOut(eResponse, response);
                        }));
        return http.build();
    }

    private void writeOut(Object object, HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=utf-8");
        try (PrintWriter out = response.getWriter()) {
            String json = oMapper.writeValueAsString(object);
            out.write(json);
        }
    }

    /**
     * add users. (@todo get users from database.)
     * 
     * @return
     */
    @Bean
    UserDetailsService userDetailsService() {
        UserDetails user = User.withDefaultPasswordEncoder()
                .username("user").password("user")
                .roles(roles("USER"))
                .build();
        UserDetails admin = User.withDefaultPasswordEncoder()
                .username("admin").password("admin")
                .roles(roles("ADMIN"))
                .build();
        return new InMemoryUserDetailsManager(user, admin);
    }

    /**
     * temporary
     * 
     * unkown reason, useless:
     * 
     * <pre>
     *
     * @Bean
     * RoleHierarchy roleHierarchy() {
     *     RoleHierarchyImpl rHierarchy = new RoleHierarchyImpl();
     *     rHierarchy.setHierarchy("ROLE_ADMIN > ROLE_USER");
     *     return rHierarchy;
     * }
     * 
     * </pre>
     * 
     * @param role
     * @return
     */
    String[] roles(String role) {
        if (StringUtils.equals("ADMIN", role)) {
            return new String[] { role, "USER" };
        }
        return new String[] { role };
    }
}
