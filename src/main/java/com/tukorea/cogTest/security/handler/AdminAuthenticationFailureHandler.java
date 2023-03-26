package com.tukorea.cogTest.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tukorea.cogTest.security.config.AdminSecurityConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
public class AdminAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Autowired
    ObjectMapper objectMapper;
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
            log.info("admin login failure");
        System.out.println("hi");
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);

            AdminSecurityConfig.JsonResponse body = new AdminSecurityConfig.JsonResponse(exception.getLocalizedMessage()+"hi", HttpStatus.UNAUTHORIZED.value(), null);
            String stringBody = objectMapper.writeValueAsString(body);
            PrintWriter writer = response.getWriter();
            writer.write(stringBody);
            writer.flush();
    }
}
