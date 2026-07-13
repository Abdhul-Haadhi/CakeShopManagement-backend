package com.example.CakeShopManagement.controller;

import com.example.CakeShopManagement.dto.ProductRecipeDto;
import com.example.CakeShopManagement.dto.RecipeDto;
import com.example.CakeShopManagement.dto.RecipeRequestDto;
import com.example.CakeShopManagement.service.RecipeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

//    @PostMapping("/recipe")
//    public RecipeDto addrecipe(@RequestBody RecipeDto dto) {
//        return recipeService.addRecipe(dto);
//    }

    @PostMapping("/recipe")
    public void saveRecipe(@RequestBody RecipeRequestDto dto){
        recipeService.saveRecipe(dto);
    }

//    @GetMapping("/recipe")
//    public List<RecipeDto> getAllRecipe() {
//        return recipeService.getAllRecipe();
//    }

    @GetMapping("/recipe/products")
    public List<ProductRecipeDto> getRecipeProducts(){
        return recipeService.getRecipeProducts();
    }

    @GetMapping("/recipe/{productId}")
    public List<RecipeDto> getRecipeByProduct(@PathVariable Long productId) {
        return recipeService.getRecipeByProduct(productId);
    }

//    @DeleteMapping("/recipe/product/{recipeId}")
//    public void deleteRecipe(@PathVariable Long recipeId) {
//        recipeService.deleteRecipe(recipeId);
//    }

    @DeleteMapping("/recipe/product/{productId}")
    public void deleteRecipe(@PathVariable Long productId) {
        recipeService.deleteRecipeByProduct(productId);
    }

}
