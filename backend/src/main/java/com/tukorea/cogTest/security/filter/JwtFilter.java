package com.tukorea.cogTest.security.filter;

import com.tukorea.cogTest.security.jwt.TokenUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if(authorizationHeader == null){
            // 토큰 헤더 없음.
        }
        String[] tokenArray = authorizationHeader.split(" ");
        if(!tokenArray[0].equals("Bearer")){
            // 인증 타입 다름
        }
        if(tokenArray.length!=2){
            // 토큰 온전하지 않음
        }
        String jwtToken = tokenArray[1];
        TokenUtil
    }
}
