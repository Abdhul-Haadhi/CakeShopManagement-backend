package com.example.CakeShopManagement.service.Impl;

import com.example.CakeShopManagement.dto.ProductRecipeDto;
import com.example.CakeShopManagement.dto.RecipeDto;
import com.example.CakeShopManagement.dto.RecipeItemDto;
import com.example.CakeShopManagement.dto.RecipeRequestDto;
import com.example.CakeShopManagement.entity.InventoryEntity;
import com.example.CakeShopManagement.entity.ProductEntity;
import com.example.CakeShopManagement.entity.RecipeEntity;
import com.example.CakeShopManagement.repository.InventoryRepository;
import com.example.CakeShopManagement.repository.ProductRegistrationRepository;
import com.example.CakeShopManagement.repository.RecipeRepository;
import com.example.CakeShopManagement.service.RecipeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;
    private final ProductRegistrationRepository productReposotory;
    private final InventoryRepository inventoryRepository;

    public RecipeServiceImpl(RecipeRepository recipeRepository, ProductRegistrationRepository productReposotory, InventoryRepository inventoryRepository) {
        this.recipeRepository = recipeRepository;
        this.productReposotory = productReposotory;
        this.inventoryRepository = inventoryRepository;
    }

//    @Override
//    public RecipeDto addRecipe(RecipeDto dto){
//
//        if(recipeRepository.existsByProductProductIdAndInventoryInventoryId(dto.getProductId(), dto.getInventoryId())){
//            throw new RuntimeException("This ingredient is already added to this product.");
//        }
//
//        ProductEntity product = productReposotory.findById(dto.getProductId()).orElseThrow();
//
//        InventoryEntity inventory = inventoryRepository.findById(dto.getInventoryId()).orElseThrow(() -> new RuntimeException("Product not found"));
//
//        RecipeEntity recipe = new RecipeEntity();
//
//        recipe.setProduct(product);
//        recipe.setInventory(inventory);
//        recipe.setQuantityRequired(dto.getQuantityRequired());
//        recipeRepository.save(recipe);
//
//        return convert(recipe);
//    }

    @Override
    public void saveRecipe(RecipeRequestDto dto){
        ProductEntity product = productReposotory.findById(dto.getProductId()).orElseThrow();

        for(RecipeItemDto item : dto.getIngredients()){
            if(recipeRepository.existsByProductProductIdAndInventoryInventoryId(dto.getProductId(), item.getInventoryId())){
                throw new RuntimeException("Ingredient already exists.");
            }
            InventoryEntity inventory = inventoryRepository.findById(item.getInventoryId()).orElseThrow();

            RecipeEntity recipe = new RecipeEntity();

            recipe.setProduct(product);
            recipe.setInventory(inventory);
            recipe.setQuantityRequired(item.getQuantityRequired());

            recipeRepository.save(recipe);
        }
    }


//    @Override
//    public List<RecipeDto> getAllRecipe(){
//
//        return recipeRepository.findAll().stream().map(this::convert).toList();
//    }

    @Override
    public List<RecipeDto> getRecipeByProduct(Long productId){
        return recipeRepository.findByProductProductId(productId).stream().map(this::convert).toList();
    }

    @Override
    public List<ProductRecipeDto> getRecipeProducts(){

        return recipeRepository.findProductsWithRecipes()
                .stream()
                .map(product -> new ProductRecipeDto(
                        product.getProductId(),
                        product.getProductName()
                ))
                .toList();

    }

//    @Override
//    public void deleteRecipe(Long recipeId){
//        recipeRepository.deleteById(recipeId);
//    }


    @Override
    public List<RecipeEntity> getRecipeEntities(Long productId){
        return recipeRepository.findByProductProductId(productId);
    }


    @Override
    @Transactional
    public void deleteRecipeByProduct(Long productId){
        List<RecipeEntity> recipes = recipeRepository.findByProductProductId(productId);
        recipeRepository.deleteAll(recipes);
    }


    public RecipeDto convert(RecipeEntity recipe){
        RecipeDto dto = new RecipeDto();

        dto.setRecipeId(recipe.getRecipeId());
        dto.setProductId(recipe.getProduct().getProductId());
        dto.setProductName(recipe.getProduct().getProductName());
        dto.setInventoryId(recipe.getInventory().getInventoryId());
        dto.setInventoryName(recipe.getInventory().getItemName());
        dto.setQuantityRequired(recipe.getQuantityRequired());

        return dto;
    }

}
