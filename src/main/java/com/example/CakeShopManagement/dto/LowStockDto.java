package com.example.CakeShopManagement.dto;

public class LowStockDto {
    private Long inventoryId;
    private String itemName;
    private Double currentQuantity;
    private Double reorderLevel;

    public LowStockDto() {
    }

    public LowStockDto(Long inventoryId, String itemName, Double currentQuantity, Double reorderLevel) {
        this.inventoryId = inventoryId;
        this.itemName = itemName;
        this.currentQuantity = currentQuantity;
        this.reorderLevel = reorderLevel;
    }

    public Long getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(Long inventoryId) {
        this.inventoryId = inventoryId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Double getCurrentQuantity() {
        return currentQuantity;
    }

    public void setCurrentQuantity(Double currentQuantity) {
        this.currentQuantity = currentQuantity;
    }

    public Double getReorderLevel() {
        return reorderLevel;
    }

    public void setReorderLevel(Double reorderLevel) {
        this.reorderLevel = reorderLevel;
    }
}
