package com.example.CakeShopManagement.dto;

import lombok.Data;

@Data
public class MonthlySalesDto {

    private String month;
    private Long orders;
    private Long revenue;

    public MonthlySalesDto() {
    }

    public MonthlySalesDto(Object month, Object orders, Object revenue) {
        this.month = month != null ? month.toString() : "";
        this.orders = orders instanceof Number ? ((Number) orders).longValue() : 0L;
        this.revenue = revenue instanceof Number ? ((Number) revenue).longValue() : 0L;
    }


    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public Long getOrders() {
        return orders;
    }

    public void setOrders(Long orders) {
        this.orders = orders;
    }

    public Long getRevenue() {
        return revenue;
    }

    public void setRevenue(Long revenue) {
        this.revenue = revenue;
    }
}
