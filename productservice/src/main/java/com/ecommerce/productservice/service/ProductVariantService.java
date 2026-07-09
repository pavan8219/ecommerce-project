package com.ecommerce.productservice.service;

import com.ecommerce.productservice.dto.ProductVariantRequestDto;
import com.ecommerce.productservice.dto.ProductVariantResponse;

import java.util.List;

public interface ProductVariantService {
    ProductVariantResponse create(ProductVariantRequestDto dto);
    ProductVariantResponse getById(Long id);
    List<ProductVariantResponse> getVariantsByProductId(Long productId);
    void delete(Long id);
}
