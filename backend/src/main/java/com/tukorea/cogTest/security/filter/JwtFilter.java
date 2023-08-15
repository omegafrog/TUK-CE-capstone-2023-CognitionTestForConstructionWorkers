package com.tukorea.cogTest.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tukorea.cogTest.domain.User;
import com.tukorea.cogTest.domain.enums.Role;
import com.tukorea.cogTest.dto.AdminDTO;
import com.tukorea.cogTest.dto.SubjectDTO;
import com.tukorea.cogTest.repository.LogoutRepository;
import com.tukorea.cogTest.response.ResponseUtil;
import com.tukorea.cogTest.security.jwt.TokenUtil;
import com.tukorea.cogTest.service.AdminService;
import com.tukorea.cogTest.service.SubjectService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentHashMap;

@RequiredArgsConstructor
@Slf4j
@Order(2)
public class JwtFilter extends OncePerRequestFilter {

    private final String secret;
    private final AdminService adminService;
    private final SubjectService subjectService;
    private final LogoutRepository logoutRepository;
    private final ObjectMapper objectMapper;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("jwt filter 시작");
        response.setContentType("application/json;charset=utf-8");
        PrintWriter writer = response.getWriter();

        // 로그인 페이지는 필요 없음.
        String requestURI = request.getRequestURI();
        if (requestURI.contains("login") || requestURI.contains("register")) {
            log.info("로그인,회원가입 페이지 진입");
            filterChain.doFilter(request, response);
            return;
        }
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authorizationHeader == null) {
            // 토큰 헤더 없음.
            log.info("토큰 헤더 없음");
            ConcurrentHashMap<String, Object> body =
                    ResponseUtil.setResponseBody(HttpStatus.INTERNAL_SERVER_ERROR, "토큰 헤더 없음.", null);
            writer.write(objectMapper.writeValueAsString(body));
            return;
        }
        String[] tokenArray = authorizationHeader.split(" ");
        if (!tokenArray[0].equals("Bearer")) {
            // 인증 타입 다름
            log.info("인증 타입 다름");
            ConcurrentHashMap<String, Object> body =
                    ResponseUtil.setResponseBody(HttpStatus.INTERNAL_SERVER_ERROR, "인증 타입 다름.", null);
            writer.write(objectMapper.writeValueAsString(body));
            return;
        }
        if (tokenArray.length != 2) {
            // 토큰 온전하지 않음
            log.info("토큰 온전하지 않음");
            ConcurrentHashMap<String, Object> body =
                    ResponseUtil.setResponseBody(HttpStatus.INTERNAL_SERVER_ERROR, "토큰 온전하지 않음.", null);
            writer.write(objectMapper.writeValueAsString(body));
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
            ConcurrentHashMap<String, Object> body =
                    ResponseUtil.setResponseBody(HttpStatus.INTERNAL_SERVER_ERROR, "토큰 만료됨.", null);
            writer.write(objectMapper.writeValueAsString(body));
            return;
        }
        String id = (String) claims.get("userId");
        String audience = claims.getAudience();
        // 로그아웃 체크
        if(logoutRepository.findByToken(jwtToken)){
            log.info("로그아웃된 유저입니다.");
            ConcurrentHashMap<String, Object> body =
                    ResponseUtil.setResponseBody(HttpStatus.INTERNAL_SERVER_ERROR, "로그아웃된 유저입니다.", null);
            writer.write(objectMapper.writeValueAsString(body));
            return;
        }

        UserDetails user=null;

        try {
            if(audience.equals(Role.SU_ADMIN.value) || audience.equals(Role.ADMIN.value)){
                AdminDTO byId = adminService.findById(Long.valueOf(id));
                user = adminService.loadUserByUsername(byId.getUsername());
            }else if(audience.equals(Role.USER.value)){
                SubjectDTO byId = subjectService.findSubject(Long.valueOf(id));
                user = subjectService.loadUserByUsername(byId.getUsername());
            }
        }catch(UsernameNotFoundException e) {
            filterChain.doFilter(request,response);
        }
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                user.getUsername(), user.getPassword(), user.getAuthorities()
        );
        SecurityContextHolder.getContext().setAuthentication(token);
        filterChain.doFilter(request,response);
    }
}
