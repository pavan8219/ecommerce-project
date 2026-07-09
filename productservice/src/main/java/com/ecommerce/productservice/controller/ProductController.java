package com.ecommerce.productservice.controller;

import com.ecommerce.productservice.dto.ProductRequestDto;
import com.ecommerce.productservice.dto.ProductResponseDto;
import com.ecommerce.productservice.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;
    @PostMapping
    public ResponseEntity<ProductResponseDto> create(@RequestBody @Valid ProductRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.create(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getById(id));
    }

    @GetMapping
    public ResponseEntity<Page<ProductResponseDto>> getAll(
            @RequestParam(defaultValue = "0")
            int page,
            @RequestParam(defaultValue = "10")
            int size,
            @RequestParam(defaultValue = "id")
            String sortBy,
            @RequestParam(defaultValue = "asc")
            String direction) {
        return ResponseEntity.ok(productService.getAll(page, size, sortBy, direction));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDto> update(@PathVariable Long id, @RequestBody ProductRequestDto dto) {
        return ResponseEntity.ok(productService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
