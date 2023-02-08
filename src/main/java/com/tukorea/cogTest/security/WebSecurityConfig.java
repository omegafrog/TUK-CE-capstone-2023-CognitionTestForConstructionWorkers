package com.tukorea.cogTest.security;

// TODO : spring security 가 h2console을 막아서 접근을 못함. webconfigurer만들어야함.

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    SecurityFilterChain web(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(
                (authorize) -> {
                    try {
                        authorize.anyRequest().permitAll()
                                .and()
                                .csrf()
                                .ignoringRequestMatchers(
                                        new AntPathRequestMatcher("/h2-console/**")
                                ).and()
                                .headers()
                                .frameOptions()
                                .sameOrigin();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });
        return http.build();
    }
}
