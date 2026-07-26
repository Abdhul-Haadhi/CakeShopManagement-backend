package com.example.CakeShopManagement.dto;

import java.util.Date;

public class RevenueChartDto {
    private String label;
    private Long revenue;

    public RevenueChartDto() {
    }

    public RevenueChartDto(Date date, Long revenue) {
        this.label = date.toString();
        this.revenue = revenue;
    }

    public String getLabel() {
        return label;
    }

    public Long getRevenue() {
        return revenue;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setRevenue(Long revenue) {
        this.revenue = revenue;
    }
}
