package com.jaeshop.modules.auth.service;

import com.jaeshop.global.exception.CustomException;
import com.jaeshop.global.exception.ErrorCode;
import com.jaeshop.global.security.JwtUtil;
import com.jaeshop.modules.auth.dto.LoginRequest;
import com.jaeshop.modules.auth.dto.RefreshTokenRequest;
import com.jaeshop.modules.auth.dto.TokenResponse;
import com.jaeshop.modules.auth.mapper.UserTokenMapper;
import com.jaeshop.modules.user.domain.User;
import com.jaeshop.modules.user.dto.UserResponse;
import com.jaeshop.modules.user.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserMapper userMapper;
    private final UserTokenMapper userTokenMapper;
    private final JwtUtil jwtUtil;

    @Value("${jwt.refresh-token-expiration}")
    private long refreshTokenExpire;

    @Override
    @Transactional
    public UserResponse login(LoginRequest req) {

        User user = userMapper.findByEmail(req.getEmail());

        if (user == null)
            throw new CustomException(ErrorCode.USER_NOT_FOUND);

        if (!BCrypt.checkpw(req.getPassword(), user.getPassword()))
            throw new CustomException(ErrorCode.PASSWORD_MISMATCH);

        String accessToken = jwtUtil.generateAccessToken(user.getId(), user.getEmail());
        String refreshToken = jwtUtil.generateRefreshToken(user.getId(), user.getEmail());

        userTokenMapper.revokeTokens(user.getId());

        Date refreshExpiry = new Date(System.currentTimeMillis() + refreshTokenExpire);
        userTokenMapper.saveRefreshToken(user.getId(), refreshToken, refreshExpiry, accessToken);

        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .phone(user.getPhone())
                .build();
    }

    @Override
    public TokenResponse refreshToken(RefreshTokenRequest req) {

        String tokenInDb = userTokenMapper.findValidToken(req.getRefreshToken());
        if (tokenInDb == null) throw new CustomException(ErrorCode.INVALID_TOKEN);

        var claims = jwtUtil.getClaims(req.getRefreshToken());
        Long userId = claims.get("userId", Long.class);
        String email = claims.getSubject();

        String newAccessToken = jwtUtil.generateAccessToken(userId, email);

        return new TokenResponse(newAccessToken);
    }
}
