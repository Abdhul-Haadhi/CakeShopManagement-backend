package com.example.CakeShopManagement.dto;

import lombok.Data;

import java.util.List;

@Data
public class OrderItemDto {

    private Long orderItemId;
    private Long quantity;
    private Long price;

    private Long productId;
    private String productName;

    private List<OrderItemCustomizationDto> customizations;

    public OrderItemDto() {
    }

    public OrderItemDto(Long orderItemId, Long quantity, Long price, Long productId, String productName, List<OrderItemCustomizationDto> customizations) {
        this.orderItemId = orderItemId;
        this.quantity = quantity;
        this.price = price;
        this.productId = productId;
        this.productName = productName;
        this.customizations = customizations;
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

    public List<OrderItemCustomizationDto> getCustomizations() {
        return customizations;
    }

    public void setCustomizations(List<OrderItemCustomizationDto> customizations) {
        this.customizations = customizations;
    }
}
