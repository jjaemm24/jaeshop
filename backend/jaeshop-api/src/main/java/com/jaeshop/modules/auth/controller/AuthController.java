package com.jaeshop.modules.auth.controller;

import com.jaeshop.global.response.ApiResponse;
import com.jaeshop.modules.auth.dto.LoginRequest;
import com.jaeshop.modules.auth.dto.RefreshTokenRequest;
import com.jaeshop.modules.auth.dto.TokenPairResponse;
import com.jaeshop.modules.auth.service.AuthService;
import com.jaeshop.modules.user.dto.UserResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ApiResponse<UserResponse> login(@RequestBody LoginRequest req) {
        return ApiResponse.ok(authService.login(req));
    }

    @PostMapping("/refresh")
    public ApiResponse<TokenPairResponse> refresh(@RequestBody @Valid RefreshTokenRequest req) {
        return ApiResponse.ok(authService.refreshToken(req));
    }

    @PostMapping("/logout")
    public ApiResponse<Void> logout(@RequestBody RefreshTokenRequest req) {
        authService.logout(req);
        return ApiResponse.ok();
    }

}