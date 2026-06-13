package com.example.CakeShopManagement.dto;


import lombok.Data;

import java.util.List;

@Data
public class AddToCartDto {

    private Long productId;

    private Long quantity;

    private String sessionId;

    private List<CartCustomizationDto> customizations;

    public AddToCartDto() {
    }

    public AddToCartDto(Long productId, Long quantity, String sessionId, List<CartCustomizationDto> customizations) {
        this.productId = productId;
        this.quantity = quantity;
        this.sessionId = sessionId;
        this.customizations = customizations;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public List<CartCustomizationDto> getCustomizations() {
        return customizations;
    }

    public void setCustomizations(List<CartCustomizationDto> customizations) {
        this.customizations = customizations;
    }
}
