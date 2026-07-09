package com.ecommerce.productservice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryDto {
    @NotBlank(message = "category cannot be empty")
    private String name;
    private String description;
    private Long parentId;
}
