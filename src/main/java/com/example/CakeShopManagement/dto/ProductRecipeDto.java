package com.example.CakeShopManagement.dto;

import lombok.Data;

@Data
public class ProductRecipeDto {

    private Long productId;
    private String productName;

    public ProductRecipeDto() {
    }

    public ProductRecipeDto(Long productId, String productName) {
        this.productId = productId;
        this.productName = productName;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}
