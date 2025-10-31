package com.jaeshop.global.config;

import com.jaeshop.global.security.JwtAuthenticationFilter;
import com.jaeshop.global.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class FilterConfig {

    private final JwtUtil jwtUtil;

    @Bean
    public FilterRegistrationBean<JwtAuthenticationFilter> jwtFilter() {
        FilterRegistrationBean<JwtAuthenticationFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new JwtAuthenticationFilter(jwtUtil));
        registrationBean.addUrlPatterns("/api/*"); // API만 필터 적용
        registrationBean.setOrder(1);
        return registrationBean;
    }
}