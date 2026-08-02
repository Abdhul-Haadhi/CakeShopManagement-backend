package com.example.CakeShopManagement.dto;

public class StockDeductionDto {
    private Double quantity;
    private String reason;

    public StockDeductionDto() {}

    public StockDeductionDto(Double quantity, String reason) {
        this.quantity = quantity;
        this.reason = reason;
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
}
