package com.tukorea.cogTest.security.handler.subject;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tukorea.cogTest.response.ResponseUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.io.IOException;

import static com.tukorea.cogTest.response.ResponseUtil.*;

@Slf4j
@RequiredArgsConstructor
public class SubjectAuthenticationFailureHandler implements AuthenticationFailureHandler {

    private final ObjectMapper objectMapper;
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        log.info("subject authentication failed");
        setRestResponseHeader(response);

        writeObjectOnResponse(response,returnWrongRequestErrorResponse(exception).getBody(), objectMapper);
    }
}
