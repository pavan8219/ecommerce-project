package com.ecommerce.userservice.service;

import com.ecommerce.userservice.dtos.LoginRequestDto;
import com.ecommerce.userservice.dtos.LoginResponseDto;
import com.ecommerce.userservice.dtos.UserCreateRequestDto;
import com.ecommerce.userservice.dtos.UserResponseDto;

public interface UserService {
    UserResponseDto registerUser(UserCreateRequestDto userCreateRequestDto);
    LoginResponseDto login(LoginRequestDto loginRequestDto);
    UserResponseDto findUserById(Long id);
}
