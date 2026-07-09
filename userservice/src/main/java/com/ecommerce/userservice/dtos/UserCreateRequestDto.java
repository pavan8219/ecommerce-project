package com.ecommerce.userservice.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserCreateRequestDto {
    @NotBlank(message = "Name is required..")
    private String name;
    @NotBlank(message = "email is required")
    @Email(message = "Valid email format is required")
    private String email;
    @NotBlank(message = "password is required")
    @Size(min = 8,message = "password should be at least 8 characters length")
    private String password;
    private String phone;
}
