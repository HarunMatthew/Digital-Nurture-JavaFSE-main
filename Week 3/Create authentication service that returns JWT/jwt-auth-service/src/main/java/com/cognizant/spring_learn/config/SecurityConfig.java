package com.cognizant.spring_learn.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Step 1: Create authentication controller and configure it in SecurityConfig.
 *
 * The /authenticate endpoint is set to permitAll() so that Spring Security's
 * default HTTP Basic filter does NOT intercept and consume the Authorization
 * header before it reaches the controller. This lets AuthenticationController
 * read and decode the header itself (Step 2).
 */
@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/authenticate").permitAll()
                .anyRequest().authenticated()
            );

        return http.build();
    }
}
