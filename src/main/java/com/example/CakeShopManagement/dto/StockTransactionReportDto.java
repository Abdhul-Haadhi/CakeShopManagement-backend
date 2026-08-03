package com.example.CakeShopManagement.dto;

import com.example.CakeShopManagement.enums.TransactionType;

import java.time.LocalDate;

public class StockTransactionReportDto {
    private Long transactionId;
    private LocalDate transactionDate;
    private String itemSku;
    private String itemName;
    private String batchNumber;
    private TransactionType transactionType;
    private Double quantity;
    private Double remainingQuantity;
    private String employeeName;

    public StockTransactionReportDto() {}

    public StockTransactionReportDto(Long transactionId, LocalDate transactionDate, String itemSku, String itemName, String batchNumber, TransactionType transactionType, Double quantity, Double remainingQuantity, String employeeName) {
        this.transactionId = transactionId;
        this.transactionDate = transactionDate;
        this.itemSku = itemSku;
        this.itemName = itemName;
        this.batchNumber = batchNumber;
        this.transactionType = transactionType;
        this.quantity = quantity;
        this.remainingQuantity = remainingQuantity;
        this.employeeName = employeeName;
    }

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDate transactionDate) {
        this.transactionDate = transactionDate;
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

    public String getBatchNumber() {
        return batchNumber;
    }

    public void setBatchNumber(String batchNumber) {
        this.batchNumber = batchNumber;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Double getRemainingQuantity() {
        return remainingQuantity;
    }

    public void setRemainingQuantity(Double remainingQuantity) {
        this.remainingQuantity = remainingQuantity;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }
}
