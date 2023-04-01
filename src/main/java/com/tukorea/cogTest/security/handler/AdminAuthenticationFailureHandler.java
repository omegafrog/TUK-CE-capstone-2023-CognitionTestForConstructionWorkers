package com.tukorea.cogTest.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tukorea.cogTest.response.JsonResponse;
import com.tukorea.cogTest.response.ResponseUtil;
import com.tukorea.cogTest.security.config.AdminSecurityConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import static com.tukorea.cogTest.response.ResponseUtil.*;

@Slf4j
@RequiredArgsConstructor
public class AdminAuthenticationFailureHandler implements AuthenticationFailureHandler {

    private final ObjectMapper objectMapper;
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        log.info("admin authentication failed");
        setRestResponseHeader(response);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        ResponseUtil.setJsonResponse(
                response,
                exception,
                objectMapper
        );
    }
}
