package com.example.CakeShopManagement.dto;


import jakarta.persistence.Column;
import jakarta.persistence.Lob;

import java.time.LocalDateTime;

public class ProductsDto {


    private Long productId;
    private String productName;
    private String description;
    private int size;
    private int quantity;
    private Long price;

//    private LocalDateTime createdAt;
//    @Column(columnDefinition = "longblob")
//    private byte[] image;
//
//    private String imageName;
//    private String imageType;


    public ProductsDto() {
    }

    public ProductsDto(Long productId, String productName, String description, int size, int quantity, Long price) {
        this.productId = productId;
        this.productName = productName;
        this.description = description;
        this.size = size;
        this.quantity = quantity;
        this.price = price;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }
}
