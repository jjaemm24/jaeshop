package com.jaeshop.modules.user.controller;

import com.jaeshop.global.response.ApiResponse;
import com.jaeshop.modules.user.dto.UserLoginRequest;
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

    /*
      회원가입 API
     */
    @PostMapping("/register")
    public ApiResponse<UserResponse> register(@Valid @RequestBody UserRegisterRequest req) {
        return ApiResponse.ok(userService.register(req));
    }

    /*
      로그인 API
     */
    @PostMapping("/login")
    public ApiResponse<?> login(@Valid @RequestBody UserLoginRequest req) {
        return ApiResponse.ok(userService.login(req));
    }
}
