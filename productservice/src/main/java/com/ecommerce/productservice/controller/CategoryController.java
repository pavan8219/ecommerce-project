package com.ecommerce.productservice.controller;

import com.ecommerce.productservice.dto.CategoryDto;
import com.ecommerce.productservice.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/categories")
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<CategoryDto> createCategory(@RequestBody CategoryDto categoryDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.createCategory(categoryDto));
    }
    @PutMapping("/{id}")
    public ResponseEntity<CategoryDto> updateCategory(@PathVariable Long id,@RequestBody CategoryDto categoryDto){
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.updateCategory(id,categoryDto));
    }

    @DeleteMapping
    public ResponseEntity<String> deleteCategory(@PathVariable Long id){
        categoryService.deleteCategory(id);
        return ResponseEntity.status(HttpStatus.OK).body("category deleted successfully");
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable Long id){
        return ResponseEntity.ok(categoryService.getCategory(id));
    }

    @GetMapping
    public ResponseEntity<List<CategoryDto>> getAllCategories(){
        return ResponseEntity.ok(categoryService.getCategories());
    }
}
