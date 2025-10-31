package com.jaeshop.modules.auth.service;

import com.jaeshop.global.exception.CustomException;
import com.jaeshop.global.exception.ErrorCode;
import com.jaeshop.global.security.JwtUtil;
import com.jaeshop.modules.auth.dto.LoginRequest;
import com.jaeshop.modules.auth.dto.RefreshTokenRequest;
import com.jaeshop.modules.auth.dto.TokenPairResponse;
import com.jaeshop.modules.auth.mapper.UserTokenMapper;
import com.jaeshop.modules.user.domain.User;
import com.jaeshop.modules.user.dto.UserResponse;
import com.jaeshop.modules.user.mapper.UserMapper;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserMapper userMapper;
    private final UserTokenMapper userTokenMapper;
    private final JwtUtil jwtUtil;

    @Value("${jwt.refresh-token-expiration}")
    private long refreshTokenExpireMs;

    @Override
    @Transactional
    public UserResponse login(LoginRequest req) {

        User user = userMapper.findByEmail(req.getEmail());

        if (user == null) {
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }

        if (!BCrypt.checkpw(req.getPassword(), user.getPassword())) {
            throw new CustomException(ErrorCode.PASSWORD_MISMATCH);
        }

        String accessToken = jwtUtil.generateAccessToken(user.getId(), user.getEmail());
        String refreshToken = jwtUtil.generateRefreshToken(user.getId(), user.getEmail());

        userTokenMapper.revokeAllByUserId(user.getId());

        LocalDateTime refreshExpiry = LocalDateTime.now().plus(Duration.ofMillis(refreshTokenExpireMs));
        userTokenMapper.saveRefreshToken(user.getId(), refreshToken, refreshExpiry);

        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .phone(user.getPhone())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    @Transactional
    public TokenPairResponse refreshToken(RefreshTokenRequest req) {

        String oldRefreshToken = req.getRefreshToken();

        var tokenRecord = userTokenMapper.findByToken(oldRefreshToken);
        if (tokenRecord == null || Boolean.TRUE.equals(tokenRecord.getRevoked())) {
            throw new CustomException(ErrorCode.INVALID_REFRESH_TOKEN);
        }
        if (tokenRecord.getExpiresAt() == null || tokenRecord.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new CustomException(ErrorCode.REFRESH_TOKEN_EXPIRED);
        }

        Claims claims = jwtUtil.getClaims(oldRefreshToken);
        Long userId = claims.get("userId", Long.class);

        User user = userMapper.findById(userId);
        if (user == null) {
            userTokenMapper.revokeToken(oldRefreshToken);
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }

        String newAccessToken  = jwtUtil.generateAccessToken(user.getId(), user.getEmail());
        String newRefreshToken  = jwtUtil.generateRefreshToken(user.getId(), user.getEmail());

        // 기존 토큰 폐기
        userTokenMapper.revokeToken(oldRefreshToken);
        LocalDateTime newRefreshExpiry = LocalDateTime.now().plus(Duration.ofMillis(refreshTokenExpireMs));
        userTokenMapper.saveRefreshToken(user.getId(), newRefreshToken, newRefreshExpiry);

        return new TokenPairResponse(newAccessToken, newRefreshToken);
    }


    @Override
    @Transactional
    public void logout(RefreshTokenRequest req) {
        String refreshToken = req.getRefreshToken();
        if (refreshToken == null || refreshToken.isBlank()) return;
        userTokenMapper.revokeToken(refreshToken);
    }

}
