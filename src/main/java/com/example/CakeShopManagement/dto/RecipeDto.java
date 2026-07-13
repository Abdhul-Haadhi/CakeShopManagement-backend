package com.example.CakeShopManagement.dto;

import lombok.Data;

@Data
public class RecipeDto {
    private Long recipeId;
    private Long productId;
    private String productName;
    private Long inventoryId;
    private String inventoryName;
    private Double quantityRequired;

    public RecipeDto() {
    }

    public RecipeDto(Long recipeId, Long productId, String productName, Long inventoryId, String inventoryName, Double quantityRequired) {
        this.recipeId = recipeId;
        this.productId = productId;
        this.productName = productName;
        this.inventoryId = inventoryId;
        this.inventoryName = inventoryName;
        this.quantityRequired = quantityRequired;
    }

    public Long getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(Long recipeId) {
        this.recipeId = recipeId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Long getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(Long inventoryId) {
        this.inventoryId = inventoryId;
    }

    public String getInventoryName() {
        return inventoryName;
    }

    public void setInventoryName(String inventoryName) {
        this.inventoryName = inventoryName;
    }

    public Double getQuantityRequired() {
        return quantityRequired;
    }

    public void setQuantityRequired(Double quantityRequired) {
        this.quantityRequired = quantityRequired;
    }
}
