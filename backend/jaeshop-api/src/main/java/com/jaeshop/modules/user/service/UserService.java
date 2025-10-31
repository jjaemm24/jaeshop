package com.jaeshop.modules.user.service;

import com.jaeshop.modules.user.dto.UserLoginRequest;
import com.jaeshop.modules.user.dto.UserRegisterRequest;
import com.jaeshop.modules.user.dto.UserResponse;

public interface UserService {

    UserResponse register(UserRegisterRequest request);

    UserResponse login(UserLoginRequest request);
}
