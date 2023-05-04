package com.spring.bot.demo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

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
public class DemoWebSecurityConfiguration {

    /**
     * request validation ignores static files.
     * 
     * @return
     */
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers("/static/**");
    }

    /**
     * all url requests validation ignores login controller
     * 
     * @param http
     * @return
     * @throws Exception
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests().anyRequest().authenticated()
                .and()
                .formLogin((login) -> login.loginPage("/login.html").loginProcessingUrl("/dologin").permitAll())
                .csrf((csrf) -> csrf.disable());
        return http.build();
    }

    /**
     * add users
     * 
     * @return
     */
    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.withDefaultPasswordEncoder()
                .username("user").password("user")
                .roles("USER")
                .build();
        UserDetails admin = User.withDefaultPasswordEncoder()
                .username("admin").password("admin")
                .roles("ADMIN", "USER")
                .build();
        return new InMemoryUserDetailsManager(user, admin);
    }

}
