package com.example.CakeShopManagement.dto;

import lombok.Data;

import java.util.List;

@Data
public class RecipeRequestDto {
    private Long productId;
    private List<RecipeItemDto> ingredients;

    public RecipeRequestDto() {
    }

    public RecipeRequestDto(Long productId, List<RecipeItemDto> ingredients) {
        this.productId = productId;
        this.ingredients = ingredients;
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
}
