package com.tukorea.cogTest.security.filter;

import com.tukorea.cogTest.dto.AdminDTO;
import com.tukorea.cogTest.dto.SubjectDTO;
import com.tukorea.cogTest.security.jwt.TokenUtil;
import com.tukorea.cogTest.service.AdminService;
import com.tukorea.cogTest.service.SubjectService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Date;

@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    @Value("${jwt.secret}")
    private String secret;
    private final AdminService adminService;
    private final SubjectService subjectService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 로그인 페이지는 필요 없음.
        String requestURI = request.getRequestURI();
        String mainResource = requestURI.split("/")[0];
        if(requestURI.contains("login") || requestURI.contains("register")){
            filterChain.doFilter(request, response);
            return;
        }
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authorizationHeader == null) {
            // 토큰 헤더 없음.
        }
        String[] tokenArray = authorizationHeader.split(" ");
        if (!tokenArray[0].equals("Bearer")) {
            // 인증 타입 다름
        }
        if (tokenArray.length != 2) {
            // 토큰 온전하지 않음
        }

        // 맞는지 확인
        String jwtToken = tokenArray[1];
        Claims claims = TokenUtil.extractClaim(secret, jwtToken);
        Date expiration = claims.getExpiration();
        String id = (String) claims.get("id");
        // 만료됨
        if (expiration.after(new Date(System.currentTimeMillis()))) {

        }
        // 로그아웃 체크

        if(mainResource.equals("admin")){
            AdminDTO byId = adminService.findById(Long.valueOf(id));
            UserDetails user = adminService.loadUserByUsername(byId.getUsername());

            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                    user.getUsername(), user.getPassword(), user.getAuthorities()
            );
            token.setAuthenticated(true);
            SecurityContextHolder.getContext().setAuthentication(token);
        }else if(mainResource.equals("subject")){
            SubjectDTO byId = subjectService.findSubject(Long.valueOf(id));
            UserDetails user = subjectService.loadUserByUsername(byId.getUsername());

            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                    user.getUsername(), user.getPassword(), user.getAuthorities()
            );
            token.setAuthenticated(true);
            SecurityContextHolder.getContext().setAuthentication(token);
        }
    }
}
