package com.example.CakeShopManagement.service.admin.category;

import com.example.CakeShopManagement.dto.CategoryDto;
import com.example.CakeShopManagement.entity.CategoryEntity;

import java.util.List;

public interface CategoryService {

    CategoryEntity createCategory(CategoryDto categoryDto);

    List<CategoryEntity> getAllCategories();

    List<CategoryDto> getCategories();

    CategoryDto updateCategory(Long categoryId, CategoryDto categoryDto);

    boolean deleteCategory(Long categoryId);
}
