package com.ecommerce.productservice.service.impl;

import com.ecommerce.productservice.dto.ProductVariantRequestDto;
import com.ecommerce.productservice.dto.ProductVariantResponse;
import com.ecommerce.productservice.entities.Product;
import com.ecommerce.productservice.entities.ProductVariant;
import com.ecommerce.productservice.repository.ProductRepository;
import com.ecommerce.productservice.repository.ProductVariantRepository;
import com.ecommerce.productservice.service.ProductVariantService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductVariantImpl implements ProductVariantService {
    private final ProductVariantRepository productVariantRepository;
    private final ProductRepository productRepository;

    @Override
    public ProductVariantResponse create(ProductVariantRequestDto dto) {
        Product product = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found with id " + dto.getProductId()));
        ProductVariant variant = new ProductVariant();
        variant.setProduct(product);
        variant.setPrice(dto.getPrice());
        variant.setDiscountPrice(dto.getDiscountPrice());
        variant.setImageUrl(dto.getImageUrl());
        variant.setSkuCode("SKU-" + UUID.randomUUID().toString());
        ProductVariant saved = productVariantRepository.save(variant);
        return mapToResponse(saved);
    }

    @Override
    public ProductVariantResponse getById(Long id) {
        ProductVariant variant =
                productVariantRepository.findById(id).orElseThrow(() -> new RuntimeException("Variant not found"));
        return mapToResponse(variant);
    }

    @Override
    public List<ProductVariantResponse> getVariantsByProductId(Long productId) {
        return productVariantRepository
                .findByProductId(productId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }
    @Override
    public void delete(Long id) {
        ProductVariant variant = productVariantRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Variant not found"));
        productVariantRepository.delete(variant);
    }

    private ProductVariantResponse mapToResponse(ProductVariant variant) {
        return ProductVariantResponse.builder()
                .id(variant.getId())
                .skuCode(variant.getSkuCode())
                .productId(variant.getProduct().getId())
                .price(variant.getPrice())
                .discountPrice(variant.getDiscountPrice())
                .imageUrl(variant.getImageUrl())
                .build();
    }
}
