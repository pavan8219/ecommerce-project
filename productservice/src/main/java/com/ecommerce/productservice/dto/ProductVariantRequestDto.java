package com.ecommerce.productservice.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductVariantRequestDto {
    @NotNull(message = "product id is required")
    private Long productId;
    @Positive(message = "price should be positive")
    private Double price;
    @Positive(message = "price should be positive")
    private Double discountPrice;
    private String imageUrl;
}
