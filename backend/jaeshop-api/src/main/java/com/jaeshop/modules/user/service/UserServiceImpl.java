package com.jaeshop.modules.user.service;


import com.jaeshop.global.exception.CustomException;
import com.jaeshop.global.exception.ErrorCode;
import com.jaeshop.modules.user.domain.User;
import com.jaeshop.modules.user.dto.UserLoginRequest;
import com.jaeshop.modules.user.dto.UserRegisterRequest;
import com.jaeshop.modules.user.dto.UserResponse;
import com.jaeshop.modules.user.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserMapper userMapper;

    @Transactional
    @Override
    public UserResponse register(UserRegisterRequest request) {

        if (userMapper.findByEmail(request.getEmail()) != null) {
            throw new CustomException(ErrorCode.DUPLICATE_EMAIL);
        }

        String encodedPassword = BCrypt.hashpw(request.getPassword(), BCrypt.gensalt());

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(encodedPassword);
        user.setName(request.getName());
        user.setPhone(request.getPhone());

        userMapper.saveUser(user);

        var varUser = userMapper.findByEmail(request.getEmail());

        return UserResponse.builder()
                .id(varUser.getId())
                .email(varUser.getEmail())
                .name(varUser.getName())
                .phone(varUser.getPhone())
                .build();
    }

    @Transactional(readOnly = true)
    @Override
    public UserResponse login(UserLoginRequest request) {

        var user = userMapper.findByEmail(request.getEmail());

        if (user == null) {
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }

        if (!BCrypt.checkpw(request.getPassword(), user.getPassword())) {
            throw new CustomException(ErrorCode.PASSWORD_MISMATCH);
        }

        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .phone(user.getPhone())
                .build();

    }
}
