package com.example.CakeShopManagement.dto;


import com.example.CakeShopManagement.enums.TransactionType;

import java.time.LocalDate;

public class StockTransactionDto {
    private Long transactionId;
    private TransactionType transactionType;
    private Double quantity;
    private String batchNumber;
    private String reason;
    private LocalDate transactionDate;

    public StockTransactionDto() {
    }

    public StockTransactionDto(Long transactionId, TransactionType transactionType, Double quantity, String batchNumber, String reason, LocalDate transactionDate) {
        this.transactionId = transactionId;
        this.transactionType = transactionType;
        this.quantity = quantity;
        this.batchNumber = batchNumber;
        this.reason = reason;
        this.transactionDate = transactionDate;
    }

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
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

    public String getBatchNumber() {
        return batchNumber;
    }

    public void setBatchNumber(String batchNumber) {
        this.batchNumber = batchNumber;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDate transactionDate) {
        this.transactionDate = transactionDate;
    }
}
