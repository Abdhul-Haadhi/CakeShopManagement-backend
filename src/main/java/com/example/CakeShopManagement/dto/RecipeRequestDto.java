package com.example.CakeShopManagement.dto;

import lombok.Data;

import java.util.List;

@Data
public class RecipeRequestDto {
    private Long productId;
    private List<RecipeItemDto> ingredients;
    private Long variantId;

    public RecipeRequestDto() {
    }

    public RecipeRequestDto(Long productId, List<RecipeItemDto> ingredients, Long variantId) {
        this.productId = productId;
        this.ingredients = ingredients;
        this.variantId = variantId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public List<RecipeItemDto> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<RecipeItemDto> ingredients) {
        this.ingredients = ingredients;
    }

    public Long getVariantId() {
        return variantId;
    }

    public void setVariantId(Long variantId) {
        this.variantId = variantId;
    }
}
