package com.example.CakeShopManagement.dto;

import com.example.CakeShopManagement.enums.InventoryUnit;
import lombok.Data;

import java.time.LocalDate;

@Data
public class InventoryReportDto {
    private String itemSku;
    private String itemName;
    private Double currentQuantity;
    private Double reorderLevel;
    private String batchNumber;
    private LocalDate expiryDate;
    private InventoryUnit unit;

    public InventoryReportDto() {
    }

    public InventoryReportDto(String itemSku, String itemName, Double currentQuantity, Double reorderLevel, String batchNumber, LocalDate expiryDate, InventoryUnit unit) {
        this.itemSku = itemSku;
        this.itemName = itemName;
        this.currentQuantity = currentQuantity;
        this.reorderLevel = reorderLevel;
        this.batchNumber = batchNumber;
        this.expiryDate = expiryDate;
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

    public String getBatchNumber() {
        return batchNumber;
    }

    public void setBatchNumber(String batchNumber) {
        this.batchNumber = batchNumber;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public InventoryUnit getUnit() {
        return unit;
    }

    public void setUnit(InventoryUnit unit) {
        this.unit = unit;
    }
}
