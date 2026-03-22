package com.example.CakeShopManagement.service.admin.category;

import com.example.CakeShopManagement.dto.CategoryDto;
import com.example.CakeShopManagement.entity.CategoryEntity;

import java.util.List;

public interface CategoryService {

    CategoryEntity createCategory(CategoryDto categoryDto);

    List<CategoryEntity> getAllCategories();
}
