package com.jaeshop.modules.user.dto;

import lombok.Getter;

@Getter
public class UserRegisterRequest {
    private String email;
    private String password;
    private String name;
    private String phone;
}
