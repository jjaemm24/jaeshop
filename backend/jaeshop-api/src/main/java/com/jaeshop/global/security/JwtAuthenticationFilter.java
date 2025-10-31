package com.jaeshop.global.security;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        // 1) 토큰 없으면 그냥 다음 필터로 (public API)
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // 2) "Bearer " 제거하고 토큰만 추출
        String token = authHeader.substring(7);

        try {
            // 3) 토큰 파싱
            Claims claims = jwtUtil.getClaims(token);

            Long userId = claims.get("userId", Long.class);
            String email = claims.getSubject();

            // 4) 요청에 유저 정보 저장
            request.setAttribute("userId", userId);
            request.setAttribute("email", email);

        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write("{\"success\":false, \"message\":\"Invalid or expired token\"}");
            return;
        }

        filterChain.doFilter(request, response);

    }
}
