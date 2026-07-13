package com.example.CakeShopManagement.dto;

import lombok.Data;

@Data
public class RecipeItemDto {

    private Long inventoryId;
    private Double quantityRequired;

    public RecipeItemDto() {
    }

    public RecipeItemDto(Long inventoryId, Double quantityRequired) {
        this.inventoryId = inventoryId;
        this.quantityRequired = quantityRequired;
    }

    public Long getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(Long inventoryId) {
        this.inventoryId = inventoryId;
    }

    public Double getQuantityRequired() {
        return quantityRequired;
    }

    public void setQuantityRequired(Double quantityRequired) {
        this.quantityRequired = quantityRequired;
    }
}
