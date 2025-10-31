package com.jaeshop.modules.auth.service;

import com.jaeshop.modules.auth.dto.*;
import com.jaeshop.modules.user.dto.UserResponse;

public interface AuthService {

    UserResponse login(LoginRequest req);

    TokenResponse refreshToken(RefreshTokenRequest req);

}