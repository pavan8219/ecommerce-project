package com.ecommerce.userservice.controller;

import com.ecommerce.userservice.dtos.LoginRequestDto;
import com.ecommerce.userservice.dtos.LoginResponseDto;
import com.ecommerce.userservice.dtos.UserCreateRequestDto;
import com.ecommerce.userservice.dtos.UserResponseDto;
import com.ecommerce.userservice.service.UserService;
import jakarta.validation.Valid;
import jakarta.ws.rs.GET;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> register(@Valid @RequestBody UserCreateRequestDto dto){
        UserResponseDto userResponseDto=userService.registerUser(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(userResponseDto);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@Valid @RequestBody LoginRequestDto dto){
        LoginResponseDto loginResponseDto=userService.login(dto);
        return ResponseEntity.ok(loginResponseDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> findUserById(@PathVariable Long id, Authentication authentication){
        UserResponseDto userResponseDto=userService.findUserById(id);
        return ResponseEntity.ok(userResponseDto);
    }

}
