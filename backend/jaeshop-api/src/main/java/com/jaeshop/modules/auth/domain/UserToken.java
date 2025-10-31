package com.jaeshop.modules.auth.domain;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UserToken {
    private Long id;
    private Long userId;
    private String refreshToken;
    private LocalDateTime expiresAt;
    private Boolean revoked;
    private LocalDateTime createdAt;
}
