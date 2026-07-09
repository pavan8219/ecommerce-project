package com.ecommerce.productservice.service.impl;

import com.ecommerce.productservice.dto.ProductRequestDto;
import com.ecommerce.productservice.dto.ProductResponseDto;
import com.ecommerce.productservice.entities.Category;
import com.ecommerce.productservice.entities.Product;
import com.ecommerce.productservice.repository.CategoryRepository;
import com.ecommerce.productservice.repository.ProductRepository;
import com.ecommerce.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public ProductResponseDto create(ProductRequestDto dto) {
        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() ->
                        new RuntimeException(
                                "Category not found with id " + dto.getCategoryId()));

        Product product = new Product();
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setBrand(dto.getBrand());
        product.setCategory(category);
        Product saved = productRepository.save(product);
        return mapToResponse(saved);
    }

    @Override
    public ProductResponseDto update(Long id, ProductRequestDto dto) {
        Product product = productRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Product not found with id " + id));

        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() ->
                        new RuntimeException(
                                "Category not found with id " + dto.getCategoryId()));

        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setBrand(dto.getBrand());
        product.setCategory(category);

        Product updated = productRepository.save(product);
        return mapToResponse(updated);
    }

    @Override
    public ProductResponseDto getById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Product not found with id " + id));
        return mapToResponse(product);
    }

    @Override
    public Page<ProductResponseDto> getAll(int page, int size, String sortBy, String direction) {
        Sort sort = direction.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageable =
                PageRequest.of(page, size, sort);
        return productRepository.findAll(pageable)
                .map(this::mapToResponse);
    }

    @Override
    public void delete(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Product not found with id " + id));
        productRepository.delete(product);
    }

    private ProductResponseDto mapToResponse(Product product) {
        return ProductResponseDto.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .brand(product.getBrand())
                .categoryId(product.getCategory().getId())
                .categoryName(product.getCategory().getName())
                .build();
    }
}
