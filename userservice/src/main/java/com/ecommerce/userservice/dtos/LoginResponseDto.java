package com.ecommerce.userservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponseDto {
    private Long id;
    private String token;
    private String tokenType="Bearer";
    private String name;
    private String email;
}
