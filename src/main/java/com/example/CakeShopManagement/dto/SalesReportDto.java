package com.example.CakeShopManagement.dto;

import java.util.List;

public class SalesReportDto {
    private Long totalOrders;
    private Long totalRevenue;
    private Long totalItemsSold;
    private Double averageOrderValue;

    private List<MonthlySalesDto> monthlySales;
    private List<TopSellingProductDto> topProducts;


    public SalesReportDto() {
    }

    public SalesReportDto(Long totalOrders, Long totalRevenue, Long totalItemsSold, Double averageOrderValue, List<MonthlySalesDto> monthlySales, List<TopSellingProductDto> topProducts) {
        this.totalOrders = totalOrders;
        this.totalRevenue = totalRevenue;
        this.totalItemsSold = totalItemsSold;
        this.averageOrderValue = averageOrderValue;
        this.monthlySales = monthlySales;
        this.topProducts = topProducts;
    }

    public Long getTotalOrders() {
        return totalOrders;
    }

    public void setTotalOrders(Long totalOrders) {
        this.totalOrders = totalOrders;
    }

    public Long getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(Long totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    public Long getTotalItemsSold() {
        return totalItemsSold;
    }

    public void setTotalItemsSold(Long totalItemsSold) {
        this.totalItemsSold = totalItemsSold;
    }

    public Double getAverageOrderValue() {
        return averageOrderValue;
    }

    public void setAverageOrderValue(Double averageOrderValue) {
        this.averageOrderValue = averageOrderValue;
    }

    public List<MonthlySalesDto> getMonthlySales() {
        return monthlySales;
    }

    public void setMonthlySales(List<MonthlySalesDto> monthlySales) {
        this.monthlySales = monthlySales;
    }

    public List<TopSellingProductDto> getTopProducts() {
        return topProducts;
    }

    public void setTopProducts(List<TopSellingProductDto> topProducts) {
        this.topProducts = topProducts;
    }
}
