package com.spring.bot.demo.security;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.sql.DataSource;

import org.apache.commons.lang3.ObjectUtils;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.bot.demo.exception.ErrorCode;
import com.spring.bot.demo.exception.ErrorResponse;
import com.spring.bot.demo.utils.DemoUtils;

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
     * authorizeHttpRequests():
     * method configures the authorization rules for the requests.
     * In this case,
     * requests to "/api/roles/admin/**"" require the ADMIN role,
     * requests to "/api/roles/user/**" require the USER role,
     * and any other request requires authentication.
     * 
     * formLogin():
     * method configures the login page and processing URL for the application. It
     * also sets up success and failure handlers for the login process
     * 
     * logout():
     * method configures the logout process for the application.
     * 
     * exceptionHandling():
     * method configures the handling of exceptions that occur during the
     * authentication process.
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
                .rememberMe(me -> me.key("demos"))
                .csrf(csrf -> csrf.disable())
                .exceptionHandling(handling -> handling
                        .authenticationEntryPoint((request, response, exp) -> {
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
     * manager users in a database.
     * 
     * @return
     */
    @Bean
    JdbcUserDetailsManager userDetailsService(@Autowired DataSource dataSource) {
        JdbcUserDetailsManager jManager = new JdbcUserDetailsManager(dataSource);
        if (!jManager.userExists("user")) {
            jManager.createUser(User.withDefaultPasswordEncoder()
                    .username("user").password("user")
                    .roles(DemoUtils.roles("USER"))
                    .build());
        }
        if (!jManager.userExists("admin")) {
            jManager.createUser(User.withDefaultPasswordEncoder()
                    .username("admin").password("admin")
                    .roles(DemoUtils.roles("ADMIN"))
                    .build());
        }
        return jManager;
    }
}
