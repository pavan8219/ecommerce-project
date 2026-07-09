package com.ecommerce.productservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductRequestDto {
    @NotBlank(message = "product name should not be blank")
    private String name;
    private String description;
    private String brand;
    @NotNull(message = "category id is required")
    private Long categoryId;
}
