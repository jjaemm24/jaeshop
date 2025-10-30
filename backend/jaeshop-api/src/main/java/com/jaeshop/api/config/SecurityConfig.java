package com.jaeshop.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())              // CSRF 비활성화 (POST 테스트용)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/**").permitAll() // /api/* 전체 접근 허용
                        .anyRequest().permitAll()               // 그 외 모든 경로 허용
                )
                .formLogin(form -> form.disable())          // 기본 로그인 폼 비활성화
                .httpBasic(httpBasic -> httpBasic.disable()); // Basic 인증 비활성화
        return http.build();
    }
}
