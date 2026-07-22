package com.example.CakeShopManagement.dto;

public class TopSellingProductDto {

    private String productName;
    private String categoryName;
    private Long qtySold;
    private Long revenue;

    public TopSellingProductDto() {
    }

    public TopSellingProductDto(String productName, String categoryName, Long qtySold, Long revenue) {
        this.productName = productName;
        this.categoryName = categoryName;
        this.qtySold = qtySold;
        this.revenue = revenue;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Long getQtySold() {
        return qtySold;
    }

    public void setQtySold(Long qtySold) {
        this.qtySold = qtySold;
    }

    public Long getRevenue() {
        return revenue;
    }

    public void setRevenue(Long revenue) {
        this.revenue = revenue;
    }
}
