package com.tukorea.cogTest.security.handler;

import com.nimbusds.openid.connect.sdk.LogoutRequest;
import com.tukorea.cogTest.repository.LogoutRepository;
import com.tukorea.cogTest.response.ResponseUtil;
import com.tukorea.cogTest.security.jwt.TokenUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;

@Slf4j
@RequiredArgsConstructor
public class LogoutHandler implements org.springframework.security.web.authentication.logout.LogoutHandler {
    @Autowired
    private LogoutRepository logoutRepository;

    private final String secret;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authorizationHeader == null) {
            // 토큰 헤더 없음.
            log.info("토큰 헤더 없음");
            return;
        }
        String[] tokenArray = authorizationHeader.split(" ");
        if (!tokenArray[0].equals("Bearer")) {
            // 인증 타입 다름
            log.info("인증 타입 다름");
            return;
        }
        if (tokenArray.length != 2) {
            // 토큰 온전하지 않음
            log.info("토큰 온전하지 않음");
            return;
        }

        // 맞는지 확인
        String jwtToken = tokenArray[1];
        Claims claims;
        try {
            claims = TokenUtil.extractClaim(secret, jwtToken);
        }catch (ExpiredJwtException e){
            log.info("토큰 만료됨");
            e.printStackTrace();
            request.setAttribute("Throwable", e);
            return;
        }
        logoutRepository.save(jwtToken, claims.getExpiration());
    }
}
