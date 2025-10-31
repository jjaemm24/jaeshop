package com.jaeshop.modules.user.controller;

import com.jaeshop.global.response.ApiResponse;
import com.jaeshop.modules.auth.dto.LoginRequest;
import com.jaeshop.modules.user.dto.UserRegisterRequest;
import com.jaeshop.modules.user.dto.UserResponse;
import com.jaeshop.modules.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ApiResponse<UserResponse> register(@RequestBody UserRegisterRequest req) {
        return ApiResponse.ok(userService.register(req));
    }

}
