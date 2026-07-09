package com.ecommerce.userservice.service.impl;

import com.ecommerce.userservice.dtos.LoginRequestDto;
import com.ecommerce.userservice.dtos.LoginResponseDto;
import com.ecommerce.userservice.dtos.UserCreateRequestDto;
import com.ecommerce.userservice.dtos.UserResponseDto;
import com.ecommerce.userservice.entities.User;
import com.ecommerce.userservice.exception.ResourceAlreadyExistsException;
import com.ecommerce.userservice.exception.ResourceNotFoundException;
import com.ecommerce.userservice.repository.UserRepository;
import com.ecommerce.userservice.service.UserService;
import com.ecommerce.userservice.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Override
    public UserResponseDto registerUser(UserCreateRequestDto userCreateRequestDto) {
        if (userRepository.existsByEmail(userCreateRequestDto.getEmail())) {
            throw new ResourceAlreadyExistsException("User already exists");
        }
        User user1=modelMapper.map(userCreateRequestDto,User.class);
        user1.setPassword(passwordEncoder.encode(userCreateRequestDto.getPassword()));
        User savedUser=userRepository.save(user1);
        return modelMapper.map(savedUser,UserResponseDto.class);
    }

    @Override
    public LoginResponseDto login(LoginRequestDto loginRequestDto) {
       User user= userRepository.findByEmail(loginRequestDto.getEmail()).orElseThrow(()->new ResourceNotFoundException("invalid credintials"));
       boolean matches=passwordEncoder.matches(loginRequestDto.getPassword(),user.getPassword());
       if(!matches){
           throw new ResourceNotFoundException("invalid credentials");
       }

       String token=jwtUtil.generateToken(user.getId(),user.getEmail(),user.getRoles());
       LoginResponseDto loginResponseDto=modelMapper.map(user,LoginResponseDto.class);
       loginResponseDto.setToken(token);

       return loginResponseDto;
    }

    @Override
    public UserResponseDto findUserById(Long id) {
        User user=userRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("user not found"));
        User saved=userRepository.save(user);
        return modelMapper.map(saved,UserResponseDto.class);
    }
}
