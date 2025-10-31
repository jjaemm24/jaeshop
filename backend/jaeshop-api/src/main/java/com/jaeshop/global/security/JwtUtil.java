package com.jaeshop.global.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    private final Key secretKey;
    private final long accessTokenExpire;
    private final long refreshTokenExpire;

    public JwtUtil(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.access-token-expiration}") long accessTokenExpire,
            @Value("${jwt.refresh-token-expiration}") long refreshTokenExpire
    ) {
            this.secretKey = Keys.hmacShaKeyFor(secret.getBytes());
            this.accessTokenExpire = accessTokenExpire;
            this.refreshTokenExpire = refreshTokenExpire;
    }

    /*
     * Access Token 생성
     */
    public String generateAccessToken(Long userId, String email) {
        return Jwts.builder()
                .setSubject(email)
                .claim("userId", userId)
                .setExpiration(new Date(System.currentTimeMillis() + accessTokenExpire))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    /*
     * Refresh Token 생성
     */
    public String generateRefreshToken(Long userId, String email) {
        return Jwts.builder()
                .setSubject(email)
                .claim("userId", userId)
                .setExpiration(new Date(System.currentTimeMillis() + refreshTokenExpire))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public Claims getClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(secretKey).build()
                .parseClaimsJws(token).getBody();
    }

}
