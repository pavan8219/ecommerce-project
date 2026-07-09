package com.ecommerce.productservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductResponseDto {
    private Long id;
    private String name;
    private String description;
    private String brand;
    private Long categoryId;
    private String categoryName;
}
