package com.tukorea.cogTest.security.config;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.tukorea.cogTest.domain.enums.Role;
import com.tukorea.cogTest.security.handler.AdminAuthenticationFailureHandler;
import com.tukorea.cogTest.security.handler.CustomAccessDeniedHandler;
import com.tukorea.cogTest.security.handler.suadmin.SuperAdminAccessDeniedHandler;
import com.tukorea.cogTest.security.handler.suadmin.SuperAdminAuthenticationFailureHandler;
import com.tukorea.cogTest.security.handler.suadmin.SuperAdminAuthenticationSuccessHandler;
import com.tukorea.cogTest.security.provider.AdminAuthenticationProvider;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;

import java.util.Map;

@Configuration
@EnableWebSecurity
@Slf4j
public class AdminSecurityConfig {


    @Autowired
    private AdminAuthenticationProvider adminAuthenticationProvider;

    @Autowired
    private ObjectMapper objectMapper;



    @Bean
    SecurityFilterChain suAdmin(HttpSecurity http) throws Exception {

        http
                .securityMatcher("/super/admin/**", "/super/admins/**")
                .authenticationProvider(adminAuthenticationProvider)
                .authorizeHttpRequests()
                .anyRequest().hasRole(Role.SU_ADMIN.value)
                .and()
                .formLogin().permitAll()
                .loginProcessingUrl("/super/admin/login")
                .usernameParameter("username")
                .passwordParameter("password")
                .successHandler(superAdminAuthenticationSuccessHandler())
                .failureHandler(superAdminAuthenticationFailureHandler())
                .and()
                .headers()
                .addHeaderWriter(new XFrameOptionsHeaderWriter(XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN))
                .and()
                .csrf()
                .disable();

        return http.build();
    }

    @Bean
    SecurityFilterChain web(HttpSecurity http) throws Exception {
        http
                .securityMatcher( "/admin/**", "/site/**")
                .authenticationProvider(adminAuthenticationProvider)
                .authorizeHttpRequests()
                .anyRequest().hasRole("USER")
                .and()
                .formLogin().permitAll()
                .loginProcessingUrl("/admin/login")
                .usernameParameter("username")
                .passwordParameter("password")
//                .successHandler(adminAuthenticationSuccessHandler())
                .failureHandler(adminAuthenticationFailureHandler())
                .and()
                .csrf()
                .ignoringRequestMatchers("/admin/login")
                .and()
                .headers()
                .addHeaderWriter(new XFrameOptionsHeaderWriter(XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN));
//                .and()
//                .exceptionHandling()
//                .accessDeniedHandler(customAccessDeniedHandler());
        return http.build();
    }

    @Bean
    SecurityFilterChain site(HttpSecurity http) throws Exception {
        http.securityMatcher("/site/**")
                .authorizeHttpRequests().anyRequest().hasRole("ADMIN");
        return http.build();

    }

    @Bean
    SuperAdminAccessDeniedHandler superAdminAccessDeniedHandler(){
        return new SuperAdminAccessDeniedHandler(objectMapper);
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
//    @Bean
//    SecurityFilterChain basic(HttpSecurity http) throws Exception {
//        http
//                .securityMatcher("/admin/**", "/subject/**", "/site/**")
//                .exceptionHandling()
//                .authenticationEntryPoint((request, response, authException) -> {
//                    response.setStatus(HttpStatus.UNAUTHORIZED.value());
//                    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
//                    JsonResponse body = new JsonResponse(authException.getLocalizedMessage(), HttpStatus.UNAUTHORIZED.value(), null);
//                    String stringBody = objectMapper.writeValueAsString(body);
//                    PrintWriter writer = response.getWriter();
//                    writer.write(stringBody);
//                    writer.flush();
//                })
//                .accessDeniedHandler(customAccessDeniedHandler());
//
//        return http.build();
//    }
    @Bean
    SuperAdminAuthenticationSuccessHandler superAdminAuthenticationSuccessHandler(){
        return new SuperAdminAuthenticationSuccessHandler(objectMapper);
    }

    @Bean
    CustomAccessDeniedHandler customAccessDeniedHandler(){
        return new CustomAccessDeniedHandler(objectMapper);
    }
    @Bean
    AdminAuthenticationFailureHandler adminAuthenticationFailureHandler(){
        return new AdminAuthenticationFailureHandler();
    }

    @Bean
    SuperAdminAuthenticationFailureHandler superAdminAuthenticationFailureHandler(){
        return new SuperAdminAuthenticationFailureHandler(objectMapper);
    }



    @Data
    @AllArgsConstructor
    public static
    class JsonResponse{
        String msg;
        int statusCode;
        Map<String, Object> results;

    }

}
