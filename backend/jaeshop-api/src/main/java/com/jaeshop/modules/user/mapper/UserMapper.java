package com.jaeshop.modules.user.mapper;

import com.jaeshop.modules.user.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {

    User findByEmail(@Param("email") String email);

    void saveUser(User user);

    User findById(@Param("id") Long id);
}
