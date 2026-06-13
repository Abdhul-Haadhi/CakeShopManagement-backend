package com.example.CakeShopManagement.dto;

import lombok.Data;

@Data
public class OrderItemDto {

    private Long orderItemId;
    private Long quantity;
    private Long price;

    private Long productId;
    private String productName;

    public OrderItemDto() {
    }

    public OrderItemDto(Long orderItemId, Long quantity, Long price, Long productId, String productName) {
        this.orderItemId = orderItemId;
        this.quantity = quantity;
        this.price = price;
        this.productId = productId;
        this.productName = productName;
    }

    public Long getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(Long orderItemId) {
        this.orderItemId = orderItemId;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
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
}
