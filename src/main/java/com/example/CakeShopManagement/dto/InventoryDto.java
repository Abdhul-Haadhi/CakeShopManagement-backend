package com.example.CakeShopManagement.dto;

import com.example.CakeShopManagement.enums.InventoryCategory;
import com.example.CakeShopManagement.enums.InventoryUnit;
import lombok.Data;

@Data
public class InventoryDto {

    private Long inventoryId;
    private String itemSku;
    private String itemName;
    private InventoryCategory category;
    private InventoryUnit unit;
    private Double reorderLevel;
    private Double currentQuantity;
    private String expiryStatus;
    private Boolean isScalable = true;

    public InventoryDto() {
    }

    public InventoryDto(Long inventoryId, String itemSku, String itemName, InventoryCategory category, InventoryUnit unit, Double reorderLevel, Double currentQuantity, String expiryStatus, Boolean isScalable) {
        this.inventoryId = inventoryId;
        this.itemSku = itemSku;
        this.itemName = itemName;
        this.category = category;
        this.unit = unit;
        this.reorderLevel = reorderLevel;
        this.currentQuantity = currentQuantity;
        this.expiryStatus = expiryStatus;
        this.isScalable = isScalable;
    }

    public Long getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(Long inventoryId) {
        this.inventoryId = inventoryId;
    }

    public String getItemSku() {
        return itemSku;
    }

    public void setItemSku(String itemSku) {
        this.itemSku = itemSku;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public InventoryCategory getCategory() {
        return category;
    }

    public void setCategory(InventoryCategory category) {
        this.category = category;
    }

    public InventoryUnit getUnit() {
        return unit;
    }

    public void setUnit(InventoryUnit unit) {
        this.unit = unit;
    }

    public Double getReorderLevel() {
        return reorderLevel;
    }

    public void setReorderLevel(Double reorderLevel) {
        this.reorderLevel = reorderLevel;
    }

    public Double getCurrentQuantity() {
        return currentQuantity;
    }

    public void setCurrentQuantity(Double currentQuantity) {
        this.currentQuantity = currentQuantity;
    }

    public String getExpiryStatus() {
        return expiryStatus;
    }

    public void setExpiryStatus(String expiryStatus) {
        this.expiryStatus = expiryStatus;
    }

    public Boolean getIsScalable() {
        return isScalable;
    }

    public void setIsScalable(Boolean scalable) {
        isScalable = scalable;
    }
}
