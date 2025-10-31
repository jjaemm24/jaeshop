package com.jaeshop.modules.user.domain;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
    private Long id;
    private String email;
    private String password;
    private String name;
    private String phone;
    private String role;
    private String status;
    private String lastLoginAt;
    private String createdAt;
    private String updatedAt;
}
