package com.example.CakeShopManagement.controller;


import com.example.CakeShopManagement.dto.CategoryDto;
import com.example.CakeShopManagement.entity.CategoryEntity;
import com.example.CakeShopManagement.service.admin.category.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employee")
public class AdminCategoryController {

    private final CategoryService categoryService;

    public AdminCategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("category")
    public ResponseEntity<CategoryEntity> createCategory(@RequestBody CategoryDto categoryDto) {
        CategoryEntity categoryEntity = categoryService.createCategory(categoryDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryEntity);
    }

    @GetMapping("categories")
    public ResponseEntity<List<CategoryEntity>> getAllCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }
}
