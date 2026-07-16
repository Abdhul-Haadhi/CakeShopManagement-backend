package com.example.CakeShopManagement.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class StockDto {
    private Long stockId;
    private Long inventoryId;
    private Double quantityAdded;
    private LocalDate expiryDate;
    private String batchNumber;
    private Double remainingQuantity;
    private String itemName;
    private LocalDate receivedDate;


    public StockDto() {
    }

    public StockDto(Long stockId, Long inventoryId, Double quantityAdded, LocalDate expiryDate, String batchNumber, Double remainingQuantity, String itemName, LocalDate receivedDate) {
        this.stockId = stockId;
        this.inventoryId = inventoryId;
        this.quantityAdded = quantityAdded;
        this.expiryDate = expiryDate;
        this.batchNumber = batchNumber;
        this.remainingQuantity = remainingQuantity;
        this.itemName = itemName;
        this.receivedDate = receivedDate;
    }

    public Long getStockId() {
        return stockId;
    }

    public void setStockId(Long stockId) {
        this.stockId = stockId;
    }

    public Long getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(Long inventoryId) {
        this.inventoryId = inventoryId;
    }

    public Double getQuantityAdded() {
        return quantityAdded;
    }

    public void setQuantityAdded(Double quantityAdded) {
        this.quantityAdded = quantityAdded;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getBatchNumber() {
        return batchNumber;
    }

    public void setBatchNumber(String batchNumber) {
        this.batchNumber = batchNumber;
    }

    public Double getRemainingQuantity() {
        return remainingQuantity;
    }

    public void setRemainingQuantity(Double remainingQuantity) {
        this.remainingQuantity = remainingQuantity;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public LocalDate getReceivedDate() {
        return receivedDate;
    }

    public void setReceivedDate(LocalDate receivedDate) {
        this.receivedDate = receivedDate;
    }
}
