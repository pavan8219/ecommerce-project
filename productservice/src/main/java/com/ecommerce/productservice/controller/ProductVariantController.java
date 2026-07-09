package com.ecommerce.productservice.controller;

import com.ecommerce.productservice.dto.ProductVariantRequestDto;
import com.ecommerce.productservice.dto.ProductVariantResponse;
import com.ecommerce.productservice.service.ProductVariantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/product-variant")
public class ProductVariantController {
    private final ProductVariantService service;

    @PostMapping
    public ResponseEntity<ProductVariantResponse> create(@RequestBody ProductVariantRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductVariantResponse>
    getById(@PathVariable Long id) {

        return ResponseEntity.ok(
                service.getById(id));
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<ProductVariantResponse>> getVariants(@PathVariable Long productId) {
        return ResponseEntity.ok(
                service.getVariantsByProductId(
                        productId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
