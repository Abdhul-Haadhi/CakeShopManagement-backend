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
    private Double quantityDeducted;
    private String deductionReason;


    public StockDto() {
    }

    public StockDto(Long stockId, Long inventoryId, Double quantityAdded, LocalDate expiryDate, String batchNumber, Double remainingQuantity, String itemName, LocalDate receivedDate, Double quantityDeducted, String deductionReason) {
        this.stockId = stockId;
        this.inventoryId = inventoryId;
        this.quantityAdded = quantityAdded;
        this.expiryDate = expiryDate;
        this.batchNumber = batchNumber;
        this.remainingQuantity = remainingQuantity;
        this.itemName = itemName;
        this.receivedDate = receivedDate;
        this.quantityDeducted = quantityDeducted;
        this.deductionReason = deductionReason;
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

    public Double getQuantityDeducted() {
        return quantityDeducted;
    }

    public void setQuantityDeducted(Double quantityDeducted) {
        this.quantityDeducted = quantityDeducted;
    }

    public String getDeductionReason() {
        return deductionReason;
    }

    public void setDeductionReason(String deductionReason) {
        this.deductionReason = deductionReason;
    }
}
