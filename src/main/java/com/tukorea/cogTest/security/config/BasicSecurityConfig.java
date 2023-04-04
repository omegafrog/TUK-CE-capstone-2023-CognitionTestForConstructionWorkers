package com.tukorea.cogTest.security.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;

@Configuration
@EnableWebSecurity
@Order(value = 0)
public class BasicSecurityConfig {
    @Value("${password.secret}")
    private String secret;

    @Bean
    PasswordEncoder passwordEncoder(){
        return new Pbkdf2PasswordEncoder(secret, 256, 30, Pbkdf2PasswordEncoder.SecretKeyFactoryAlgorithm.PBKDF2WithHmacSHA512);
    }



}
