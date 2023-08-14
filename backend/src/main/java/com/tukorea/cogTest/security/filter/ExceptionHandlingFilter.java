package com.tukorea.cogTest.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tukorea.cogTest.response.ResponseUtil;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.ConcurrentHashMap;

@RequiredArgsConstructor
@Order(1)
public class ExceptionHandlingFilter implements Filter {

    private final ObjectMapper objectMapper;
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            chain.doFilter(request,response);
        } catch (RuntimeException e) {
            HttpServletResponse res = (HttpServletResponse) response;
            ConcurrentHashMap<String, Object> body =
                    ResponseUtil.setResponseBody(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), null);
            PrintWriter writer = res.getWriter();
            writer.write(objectMapper.writeValueAsString(body));
        }
    }
}
