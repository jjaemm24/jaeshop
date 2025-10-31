package com.jaeshop.modules.user.service;

import com.jaeshop.modules.auth.dto.LoginRequest;
import com.jaeshop.modules.auth.dto.RefreshTokenRequest;
import com.jaeshop.modules.auth.dto.TokenResponse;
import com.jaeshop.modules.user.dto.*;

public interface UserService {

    UserResponse register(UserRegisterRequest request);

}
