package com.example.CakeShopManagement.controller;


import com.example.CakeShopManagement.dto.CategoryDto;
import com.example.CakeShopManagement.entity.CategoryEntity;
import com.example.CakeShopManagement.exceptions.AppException;
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

//    @GetMapping("category")
//    public ResponseEntity<List<CategoryDto>> getCategory() {
//        try {
//            List<CategoryDto> categoryDtoList = categoryService.getCategories();
//            return ResponseEntity.ok(categoryDtoList);
//        }
//        catch (Exception e) {
//            throw new AppException("Request failed with error: " + e, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

    @PutMapping("categories/{categoryId}")
    public ResponseEntity<CategoryDto> updateCategory(@PathVariable Long categoryId, @RequestBody CategoryDto categoryDto) {
        try {
            CategoryDto categoryDto1 = categoryService.updateCategory(categoryId, categoryDto);
            return ResponseEntity.ok(categoryDto1);
        }
        catch (Exception e){
            throw new AppException("Request failed with error: " + e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @DeleteMapping("category/{categoryId}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long categoryId) {
        boolean deleted = categoryService.deleteCategory(categoryId);
        if(deleted){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}
