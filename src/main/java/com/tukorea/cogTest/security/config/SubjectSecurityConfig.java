package com.tukorea.cogTest.security.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tukorea.cogTest.domain.enums.Role;
import com.tukorea.cogTest.security.handler.subject.SubjectAccessDeniedHandler;
import com.tukorea.cogTest.security.handler.subject.SubjectAuthenticationFailureHandler;
import com.tukorea.cogTest.security.handler.subject.SubjectAuthenticationSuccessHandler;
import com.tukorea.cogTest.security.provider.SubjectAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SubjectSecurityConfig {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SubjectAuthenticationProvider subjectAuthenticationProvider;
    @Bean
    SubjectAuthenticationSuccessHandler subjectAuthenticationSuccessHandler(){
        return new SubjectAuthenticationSuccessHandler(objectMapper);
    }

    @Bean
    SubjectAuthenticationFailureHandler subjectAuthenticationFailureHandler(){
        return new SubjectAuthenticationFailureHandler(objectMapper);
    }
    @Bean
    SubjectAccessDeniedHandler subjectAccessDeniedHandler(){
        return new SubjectAccessDeniedHandler(objectMapper);
    }

    @Bean
    SecurityFilterChain subject(HttpSecurity http) throws Exception {
        http
                .securityMatcher("/subject/**")
                .authenticationProvider(subjectAuthenticationProvider)
                .authorizeHttpRequests()
                .anyRequest().hasAnyRole(Role.USER.value+Role.ADMIN.value)
                .and()
                .formLogin().permitAll()
                .loginProcessingUrl("/subject/login")
                .usernameParameter("username")
                .passwordParameter("password")
                .successHandler(subjectAuthenticationSuccessHandler())
                .failureHandler(subjectAuthenticationFailureHandler())
                .and()
                .exceptionHandling()
                .accessDeniedHandler(subjectAccessDeniedHandler())
                .and()
                .csrf()
                .ignoringRequestMatchers("/subject/login");

        return http.build();
    }
}
