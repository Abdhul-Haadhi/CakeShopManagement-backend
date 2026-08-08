package com.example.CakeShopManagement.entity;

import com.example.CakeShopManagement.enums.TransactionType;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "stock_transaction")
public class StockTransactionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionId;

    @ManyToOne
    @JoinColumn(name = "stock_id")
    private StockEntity stock;

    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    private Double quantity;
    private String reason;
    private LocalDate transactionDate;

    // StockTransactionEntity.java

    // REMOVE cascade = CascadeType.ALL
    @ManyToOne
    @JoinColumn(name = "employee_id")
    private EmployeeEntity employee;


    public StockTransactionEntity() {
    }

    public StockTransactionEntity(Long transactionId, StockEntity stock, TransactionType transactionType, Double quantity, String reason, LocalDate transactionDate, EmployeeEntity employee) {
        this.transactionId = transactionId;
        this.stock = stock;
        this.transactionType = transactionType;
        this.quantity = quantity;
        this.reason = reason;
        this.transactionDate = transactionDate;
        this.employee = employee;
    }

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    public StockEntity getStock() {
        return stock;
    }

    public void setStock(StockEntity stock) {
        this.stock = stock;
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

    public EmployeeEntity getEmployee() {
        return employee;
    }

    public void setEmployee(EmployeeEntity employee) {
        this.employee = employee;
    }
}
