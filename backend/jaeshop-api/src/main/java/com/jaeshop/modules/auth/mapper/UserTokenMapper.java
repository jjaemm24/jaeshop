package com.jaeshop.modules.auth.mapper;

import com.jaeshop.modules.auth.domain.UserToken;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;

@Mapper
public interface UserTokenMapper {

    void saveRefreshToken(
            @Param("userId") Long userId,
            @Param("refreshToken") String refreshToken,
            @Param("expiresAt") LocalDateTime expiresAt
    );

    UserToken findByToken(@Param("refreshToken") String refreshToken);

    void revokeToken(@Param("refreshToken") String refreshToken);

    void revokeAllByUserId(@Param("userId") Long userId);

}
