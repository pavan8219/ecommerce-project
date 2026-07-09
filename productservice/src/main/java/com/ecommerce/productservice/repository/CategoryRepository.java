package com.ecommerce.productservice.repository;

import com.ecommerce.productservice.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {
    List<Category> findByParentId(Long id);
    boolean existsByName(String name);
}
