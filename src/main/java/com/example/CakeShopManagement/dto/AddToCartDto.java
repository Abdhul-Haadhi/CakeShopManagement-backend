package com.example.CakeShopManagement.dto;


import lombok.Data;

@Data
public class AddToCartDto {

    private Long productId;

    private Long quantity;

    private String sessionId;

    public AddToCartDto() {
    }

    public AddToCartDto(Long productId, Long quantity, String sessionId) {
        this.productId = productId;
        this.quantity = quantity;
        this.sessionId = sessionId;
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
}
