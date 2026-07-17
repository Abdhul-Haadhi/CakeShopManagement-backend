package com.example.CakeShopManagement.service;

import com.example.CakeShopManagement.dto.ProductRecipeDto;
import com.example.CakeShopManagement.dto.RecipeDto;
import com.example.CakeShopManagement.dto.RecipeRequestDto;
import com.example.CakeShopManagement.entity.RecipeEntity;

import java.util.List;

public interface RecipeService {

//    RecipeDto addRecipe(RecipeDto dto);
    void saveRecipe(RecipeRequestDto dto);
//    List<RecipeDto> getAllRecipe();
    List<ProductRecipeDto> getRecipeProducts();
    List<RecipeDto> getRecipeByProduct(Long productId);
    List<RecipeEntity> getRecipeEntities(Long productId, String variantType, Integer variantValue);
//    void deleteRecipe(Long recipeId);
    void deleteRecipeByProduct(Long productId);
}
