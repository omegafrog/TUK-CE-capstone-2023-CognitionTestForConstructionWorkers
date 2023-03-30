package com.tukorea.cogTest.security.config;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.tukorea.cogTest.domain.enums.Role;
import com.tukorea.cogTest.security.entrypoint.Http401ResponseEntryPoint;
import com.tukorea.cogTest.security.handler.AdminAuthenticationFailureHandler;
import com.tukorea.cogTest.security.handler.SuperAdminAccessDeniedHandler;
import com.tukorea.cogTest.security.handler.SuperAdminAuthenticationFailureHandler;
import com.tukorea.cogTest.security.handler.SuperAdminAuthenticationSuccessHandler;
import com.tukorea.cogTest.security.provider.AdminAuthenticationProvider;
import com.tukorea.cogTest.security.provider.SubjectAuthenticationProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;


@Configuration
@EnableWebSecurity
@Slf4j
public class AdminSecurityConfig {
//
//
//
//
//
//
//    @Autowired
//    private SubjectAuthenticationProvider subjectAuthenticationProvider;
//
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//
//    @Bean
//    SecurityFilterChain subject(HttpSecurity http) throws Exception {
//        http
//                .securityMatcher("/subject/**")
//                .authenticationProvider(subjectAuthenticationProvider)
//                .authorizeHttpRequests()
//                .anyRequest().hasRole(Role.ROLE_USER.value)
//                .and()
//                .formLogin().permitAll()
//                .loginProcessingUrl("/subject/login")
//                .usernameParameter("username")
//                .passwordParameter("password")
//                .and()
//                .csrf()
//                .ignoringRequestMatchers("/subject/login");
//        return http.build();
//    }


//    @Bean
//    SecurityFilterChain web(HttpSecurity http) throws Exception {
//        http
//                .securityMatcher( "/admin/**", "/site/**")
//                .authenticationProvider(adminAuthenticationProvider)
//                .authorizeHttpRequests()
//                .anyRequest().hasRole("USER")
//                .and()
//                .formLogin().permitAll()
//                .loginProcessingUrl("/admin/login")
//                .usernameParameter("username")
//                .passwordParameter("password")
////                .successHandler(adminAuthenticationSuccessHandler())
//                .failureHandler(adminAuthenticationFailureHandler())
//                .and()
//                .csrf()
//                .ignoringRequestMatchers("/admin/login")
//                .and()
//                .headers()
//                .addHeaderWriter(new XFrameOptionsHeaderWriter(XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN));
////                .and()
////                .exceptionHandling()
////                .accessDeniedHandler(customAccessDeniedHandler());
//        return http.build();
//    }




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
    AdminAuthenticationFailureHandler adminAuthenticationFailureHandler(){
        return new AdminAuthenticationFailureHandler();
    }

}
