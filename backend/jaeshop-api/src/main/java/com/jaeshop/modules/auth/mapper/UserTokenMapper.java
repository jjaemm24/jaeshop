package com.jaeshop.modules.auth.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

@Mapper
public interface UserTokenMapper {

    void revokeTokens(@Param("userId") Long userId);

    void saveRefreshToken(
            @Param("userId") Long userId,
            @Param("refreshToken") String refreshToken,
            @Param("expiresAt") Date expiresAt,
            @Param("accessToken") String accessToken
    );

    String findValidToken(
            @Param("refreshToken") String refreshToken
    );

}
