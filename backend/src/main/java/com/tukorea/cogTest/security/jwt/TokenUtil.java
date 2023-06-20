package com.tukorea.cogTest.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;
import java.util.Map;

public class TokenUtil {



    public static TokenInfo generateToken(String payload, String secret){
        int expirationTime = 10*60*60;
        String token = getToken(payload, expirationTime, secret);
        return TokenInfo.builder()
                .accessToken(token)
                .grantType("bearer")
                .build();
    }

    private static String getToken(String payload, int expirationTime, String secret) {
        return Jwts.builder().setClaims(Map.of("id", payload))
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public static String expireToken(TokenInfo tokenInfo, String secret){
        String accessToken = tokenInfo.getAccessToken();
        String id = (String) extractClaim(secret, accessToken).get("id");
        String expiredToken = getToken(id, 0, secret);
        return expiredToken;
    }

    public static Claims extractClaim(String secret, String accessToken) {
        return Jwts.parser().setSigningKey(secret)
                .parseClaimsJws(accessToken).getBody();
    }
}
