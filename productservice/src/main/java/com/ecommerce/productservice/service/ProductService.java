package com.ecommerce.productservice.service;

import com.ecommerce.productservice.dto.ProductRequestDto;
import com.ecommerce.productservice.dto.ProductResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ProductService {
    ProductResponseDto create(ProductRequestDto dto);
    ProductResponseDto update(Long id, ProductRequestDto dto);
    ProductResponseDto getById(Long id);
    Page<ProductResponseDto> getAll(
            int page,
            int size,
            String sortBy,
            String sortDirection);
    void delete(Long id);
}
