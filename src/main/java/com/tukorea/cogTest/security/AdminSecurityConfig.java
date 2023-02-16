package com.tukorea.cogTest.security;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.tukorea.cogTest.domain.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;

import java.io.PrintWriter;
import java.util.Map;

@Configuration
@EnableWebSecurity
public class AdminSecurityConfig {
    @Value ("${password.secret}")
    private String secret;

    @Bean
    PasswordEncoder passwordEncoder(){
        return new Pbkdf2PasswordEncoder(secret, 256, 30, Pbkdf2PasswordEncoder.SecretKeyFactoryAlgorithm.PBKDF2WithHmacSHA512);
    }

    @Autowired
    private AdminAuthenticationProvider adminAuthenticationProvider;

    @Autowired
    private SubjectAuthenticationProvider subjectAuthenticationProvider;
    @Autowired
    private ObjectMapper objectMapper;


    @Bean
    SecurityFilterChain subject(HttpSecurity http) throws Exception {
        http
                .securityMatcher("/subject/**")
                .authenticationProvider(subjectAuthenticationProvider)
                .authorizeHttpRequests()
                .anyRequest().hasRole(Role.ROLE_USER.value)
                .and()
                .formLogin().permitAll()
                .loginProcessingUrl("/subject/login")
                .usernameParameter("username")
                .passwordParameter("password")
                .and()
                .csrf()
                .ignoringRequestMatchers("/subject/login")
                .and()
                .httpBasic();
        return http.build();
    }

    @Bean
    SecurityFilterChain web(HttpSecurity http) throws Exception {
        http
                .securityMatcher("/admin/**")
                .authenticationProvider(adminAuthenticationProvider)
                .authorizeHttpRequests()
                .anyRequest().hasRole("USER")
                .and()
                .formLogin().permitAll()
                .loginProcessingUrl("/admin/login")
                .usernameParameter("username")
                .passwordParameter("password")
                .failureHandler((request, response, exception) -> {
                    response.setStatus(HttpStatus.UNAUTHORIZED.value());
                    response.setContentType(MediaType.APPLICATION_JSON_VALUE);

                    JsonResponse body = new JsonResponse(exception.getLocalizedMessage(), HttpStatus.UNAUTHORIZED.value(), null);
                    String stringBody = objectMapper.writeValueAsString(body);
                    PrintWriter writer = response.getWriter();
                    writer.write(stringBody);
                    writer.flush();
                })
                .and()
                .csrf()
                .ignoringRequestMatchers("/admin/login")
                .and()
                .httpBasic();

        return http.build();
    }

    @Bean
    SecurityFilterChain site(HttpSecurity http) throws Exception {
        http.securityMatcher("/site/**")
                .authorizeHttpRequests().anyRequest().hasRole("ADMIN");
        return http.build();

    }

    @Bean
    SecurityFilterChain h2console(HttpSecurity http) throws Exception {
        http.securityMatcher("/h2-console/**")
                .authorizeHttpRequests().anyRequest().permitAll()
                .and()
                .csrf()
                .ignoringRequestMatchers("/h2-console/**")
                .and()
                .headers()
                .addHeaderWriter(new XFrameOptionsHeaderWriter(XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN));
        return http.build();
    }
    @Bean
    SecurityFilterChain basic(HttpSecurity http) throws Exception {
        http
                .securityMatcher("/admin/**", "/subject/**", "/site/**")
                .exceptionHandling()
                .authenticationEntryPoint((request, response, authException) -> {
                    response.setStatus(HttpStatus.UNAUTHORIZED.value());
                    response.setContentType(MediaType.APPLICATION_JSON_VALUE);

                    JsonResponse body = new JsonResponse(authException.getLocalizedMessage(), HttpStatus.UNAUTHORIZED.value(), null);
                    String stringBody = objectMapper.writeValueAsString(body);
                    PrintWriter writer = response.getWriter();
                    writer.write(stringBody);
                    writer.flush();
                })
                .accessDeniedHandler((request, response, accessDeniedException) -> {
                    response.setStatus(HttpStatus.UNAUTHORIZED.value());
                    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                    JsonResponse body = new JsonResponse(accessDeniedException.getLocalizedMessage(), HttpStatus.UNAUTHORIZED.value(), null);
                    String stringBody = objectMapper.writeValueAsString(body);
                    PrintWriter writer = response.getWriter();
                    writer.write(stringBody);
                    writer.flush();

                });
        return http.build();
    }

    @Data
    @AllArgsConstructor
    private class JsonResponse{
        String msg;
        int statusCode;
        Map<String, Object> results;

    }

}
