package com.example.CakeShopManagement.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "stock")
@Data
public class StockEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long stockId;

    @ManyToOne
    @JoinColumn(name = "inventory_id")
    private InventoryEntity inventory;

    private Double quantityAdded;
    private LocalDate expiryDate;
    private LocalDate receivedDate;

    @Column(nullable = false,unique = true)
    private String batchNumber;
    private Double remainingQuantity;
//    private Double quantityDeducted = 0.0;
//
//    @Column(length = 255)
//    private String deductionReason;

    @OneToMany(mappedBy = "stock", cascade = CascadeType.ALL)
    private List<StockTransactionEntity> transactions = new ArrayList<>();

    public StockEntity() {
    }

    public StockEntity(Long stockId, InventoryEntity inventory, Double quantityAdded, LocalDate expiryDate, LocalDate receivedDate, String batchNumber, Double remainingQuantity, List<StockTransactionEntity> transactions) {
        this.stockId = stockId;
        this.inventory = inventory;
        this.quantityAdded = quantityAdded;
        this.expiryDate = expiryDate;
        this.receivedDate = receivedDate;
        this.batchNumber = batchNumber;
        this.remainingQuantity = remainingQuantity;
        this.transactions = transactions;
    }

    public Long getStockId() {
        return stockId;
    }

    public void setStockId(Long stockId) {
        this.stockId = stockId;
    }

    public InventoryEntity getInventory() {
        return inventory;
    }

    public void setInventory(InventoryEntity inventory) {
        this.inventory = inventory;
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

    public LocalDate getReceivedDate() {
        return receivedDate;
    }

    public void setReceivedDate(LocalDate receivedDate) {
        this.receivedDate = receivedDate;
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

    public List<StockTransactionEntity> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<StockTransactionEntity> transactions) {
        this.transactions = transactions;
    }
}
