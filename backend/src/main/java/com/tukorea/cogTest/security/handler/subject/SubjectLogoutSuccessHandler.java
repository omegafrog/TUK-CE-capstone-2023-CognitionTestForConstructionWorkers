package com.tukorea.cogTest.security.handler.subject;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tukorea.cogTest.response.ResponseUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.tukorea.cogTest.response.ResponseUtil.writeObjectOnResponse;

@Slf4j
@RequiredArgsConstructor
public class SubjectLogoutSuccessHandler implements LogoutSuccessHandler {

    private final ObjectMapper objectMapper;
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("subject logout success");
        ResponseUtil.setRestResponseHeader(response);

        ConcurrentHashMap<String, Object> result = ResponseUtil.setResponseBody(HttpStatus.OK, "subject logout success", null);
        writeObjectOnResponse(response, result, objectMapper);
        response.setStatus(HttpStatus.OK.value());
    }
}
