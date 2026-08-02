package com.example.CakeShopManagement.service.Impl;

import com.example.CakeShopManagement.dto.*;
import com.example.CakeShopManagement.entity.InventoryEntity;
import com.example.CakeShopManagement.entity.ProductEntity;
import com.example.CakeShopManagement.entity.ProductVariant;
import com.example.CakeShopManagement.entity.RecipeEntity;
import com.example.CakeShopManagement.repository.InventoryRepository;
import com.example.CakeShopManagement.repository.ProductRegistrationRepository;
import com.example.CakeShopManagement.repository.ProductVariantRepository;
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
    private final ProductVariantRepository productVariantRepository;

    public RecipeServiceImpl(RecipeRepository recipeRepository, ProductRegistrationRepository productReposotory, InventoryRepository inventoryRepository, ProductVariantRepository productVariantRepository) {
        this.recipeRepository = recipeRepository;
        this.productReposotory = productReposotory;
        this.inventoryRepository = inventoryRepository;
        this.productVariantRepository = productVariantRepository;
    }

//    @Override
//    public RecipeDto addRecipe(RecipeDto dto){
//
//        if(recipeRepository.existsByProductProductIdAndProductVariantVariantIdAndInventoryInventoryId(dto.getProductId(), dto.getInventoryId())){
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
        ProductVariant variant = productVariantRepository.findById(dto.getVariantId()).orElseThrow();

        for(RecipeItemDto item : dto.getIngredients()){
            if(recipeRepository.existsByProductProductIdAndProductVariantVariantIdAndInventoryInventoryId(dto.getProductId(), dto.getVariantId(), item.getInventoryId())){
                throw new RuntimeException("Ingredient already exists for this variant.");
            }
            InventoryEntity inventory = inventoryRepository.findById(item.getInventoryId()).orElseThrow();

            RecipeEntity recipe = new RecipeEntity();

            recipe.setProduct(product);
            recipe.setProductVariant(variant);
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
    public RecipeDetailsDto getRecipeByProduct(Long productId) {

        List<RecipeEntity> recipes = recipeRepository.findByProductProductId(productId);

        if (recipes.isEmpty()) {
            throw new RuntimeException("Recipe not found");
        }

        RecipeEntity firstRecipe = recipes.get(0);

        RecipeDetailsDto details = new RecipeDetailsDto();

        details.setProductId(firstRecipe.getProduct().getProductId());
        details.setProductName(firstRecipe.getProduct().getProductName());

        details.setVariantId(firstRecipe.getProductVariant().getVariantId());
        details.setVariantType(firstRecipe.getProductVariant().getVariantType().name());

        if (firstRecipe.getProductVariant().getWeight() != null) {
            details.setWeight(firstRecipe.getProductVariant().getWeight());
        } else {
            details.setPieces(firstRecipe.getProductVariant().getPieces());
        }

        List<RecipeDto> ingredientList = recipes.stream()
                .map(this::convert)
                .toList();

        details.setIngredients(ingredientList);

        return details;
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
    public List<RecipeEntity> getRecipeEntities(Long productId, String variantType, Integer variantValue) {

        List<RecipeEntity> recipes = recipeRepository.findByProductProductId(productId);

        if(variantValue == null){

            ProductVariant smallest = recipes.stream()
                    .map(RecipeEntity::getProductVariant)
                    .filter(v -> v.getVariantType().name().equals(variantType))
                    .min((a,b)->{

                        int va = a.getWeight()!=null ? a.getWeight() : a.getPieces();
                        int vb = b.getWeight()!=null ? b.getWeight() : b.getPieces();

                        return Integer.compare(va,vb);

                    })
                    .orElse(null);

            if(smallest==null){
                return List.of();
            }

            Long variantId = smallest.getVariantId();

            return recipes.stream()
                    .filter(r->r.getProductVariant().getVariantId().equals(variantId))
                    .toList();
        }

        return recipes.stream().filter(recipe->{

                    ProductVariant variant = recipe.getProductVariant();

                    if(!variant.getVariantType().name().equals(variantType))
                        return false;

                    if(variant.getWeight()!=null)
                        return variant.getWeight().equals(variantValue);

                    return variant.getPieces().equals(variantValue);

                })
                .toList();
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
        dto.setVariantId(recipe.getProductVariant().getVariantId());
        dto.setVariantType(recipe.getProductVariant().getVariantType().name());

        if(recipe.getProductVariant().getWeight() != null){
            dto.setVariantValue(recipe.getProductVariant().getWeight());
        }
        else {
            dto.setVariantValue(recipe.getProductVariant().getPieces());
        }

        return dto;
    }

}
