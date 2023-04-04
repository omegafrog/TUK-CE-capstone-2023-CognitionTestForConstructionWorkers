package com.tukorea.cogTest.security.handler.subject;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tukorea.cogTest.response.ResponseUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class SubjectAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper objectMapper;
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        log.info("subject authorization failed");

        ResponseUtil.setRestResponseHeader(response);
        ResponseUtil.returnWrongRequestErrorResponse(accessDeniedException);
    }
}
