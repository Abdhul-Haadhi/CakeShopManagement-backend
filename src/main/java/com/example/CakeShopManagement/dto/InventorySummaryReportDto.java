package com.example.CakeShopManagement.dto;

import com.example.CakeShopManagement.enums.InventoryUnit;

public class InventorySummaryReportDto {
    private String itemSku;
    private String itemName;
    private Double currentQuantity;
    private Double reorderLevel;
    private InventoryUnit unit;

    public InventorySummaryReportDto() {
    }

    public InventorySummaryReportDto(String itemSku, String itemName, Double currentQuantity, Double reorderLevel, InventoryUnit unit) {
        this.itemSku = itemSku;
        this.itemName = itemName;
        this.currentQuantity = currentQuantity;
        this.reorderLevel = reorderLevel;
        this.unit = unit;
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

    public InventoryUnit getUnit() {
        return unit;
    }

    public void setUnit(InventoryUnit unit) {
        this.unit = unit;
    }
}
