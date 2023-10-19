package com.tukorea.cogTest.security.jwt;

import com.tukorea.cogTest.domain.enums.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;
import java.util.Map;

public class TokenUtil {



    public static TokenInfo generateToken(String payload, String secret, Role audienceRole){
        int expirationTime = 1000*60*60;
        String token = getToken(payload, expirationTime, secret, audienceRole.value);
        return TokenInfo.builder()
                .accessToken(token)
                .grantType("Bearer")
                .build();
    }

    private static String getToken(String payload, int expirationTime, String secret, String audienceRole) {
        return Jwts.builder().addClaims(Map.of("userId", payload))
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setAudience(audienceRole)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public static String expireToken(TokenInfo tokenInfo, String secret){
        String accessToken = tokenInfo.getAccessToken();
        String id = (String) extractClaim(secret, accessToken).get("id");
        String audienceRole = extractClaim(secret, accessToken).getAudience();
        String expiredToken = getToken(id, 0, secret, audienceRole);
        return expiredToken;
    }

    public static Claims extractClaim(String secret, String accessToken) {
        return Jwts.parser().setSigningKey(secret)
                .parseClaimsJws(accessToken).getBody();
    }
}
