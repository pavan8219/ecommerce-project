package com.ecommerce.productservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductVariantResponse {
    private Long id;
    private String skuCode;
    private Long productId;
    private Double price;
    private Double discountPrice;
    private String imageUrl;
}
