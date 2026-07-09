package com.ecommerce.productservice.service.impl;

import com.ecommerce.productservice.dto.CategoryDto;
import com.ecommerce.productservice.entities.Category;
import com.ecommerce.productservice.repository.CategoryRepository;
import com.ecommerce.productservice.service.CategoryService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category category=modelMapper.map(categoryDto,Category.class);
        Category saved=categoryRepository.save(category);
        return modelMapper.map(saved,CategoryDto.class);
    }

    @Override
    @Transactional
    public CategoryDto updateCategory(Long id, CategoryDto categoryDto) {
        Category category=categoryRepository.findById(id).orElseThrow(()->new RuntimeException("category not found"));
        category.setName(categoryDto.getName());
        category.setDescription(categoryDto.getDescription());
        category.setParentId(categoryDto.getParentId());
        Category saved=categoryRepository.save(category);
        return modelMapper.map(saved,CategoryDto.class);
    }

    @Override
    public void deleteCategory(Long id) {
        Category category=categoryRepository.findById(id).orElseThrow(()->new RuntimeException("category not found"));
        categoryRepository.deleteById(id);
    }

    @Override
    public CategoryDto getCategory(Long id) {
        Category category=categoryRepository.findById(id).orElseThrow(()->new RuntimeException("category not found"));
        return modelMapper.map(category,CategoryDto.class);
    }

    @Override
    public List<CategoryDto> getCategories() {
        List<Category> categories=categoryRepository.findAll();
        return categories.stream().map(category -> modelMapper.map(category, CategoryDto.class)).toList();
    }
}
