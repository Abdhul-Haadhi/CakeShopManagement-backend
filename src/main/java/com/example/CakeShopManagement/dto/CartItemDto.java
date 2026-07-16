package com.example.CakeShopManagement.dto;

import lombok.Data;

import java.util.List;

@Data
public class CartItemDto {
    private Long cartId;
    private String sessionId;
    private Long quantity;
    private Long price;

    private Long productId;
    private String productName;
    private byte[] byteImage;

    private List<CartCustomizationDto> customizations;

    private Long customerId;

//    private Long variantId;
    private Integer weight;
    private Long unitPrice;

    public CartItemDto() {
    }

    public CartItemDto(Long cartId, String sessionId, Long quantity, Long price, Long productId, String productName, byte[] byteImage, List<CartCustomizationDto> customizations, Long customerId, Integer weight, Long unitPrice) {
        this.cartId = cartId;
        this.sessionId = sessionId;
        this.quantity = quantity;
        this.price = price;
        this.productId = productId;
        this.productName = productName;
        this.byteImage = byteImage;
        this.customizations = customizations;
        this.customerId = customerId;
        this.weight = weight;
        this.unitPrice = unitPrice;
    }

    public Long getCartId() {
        return cartId;
    }

    public void setCartId(Long cartId) {
        this.cartId = cartId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
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

    public byte[] getByteImage() {
        return byteImage;
    }

    public void setByteImage(byte[] byteImage) {
        this.byteImage = byteImage;
    }

    public List<CartCustomizationDto> getCustomizations() {
        return customizations;
    }

    public void setCustomizations(List<CartCustomizationDto> customizations) {
        this.customizations = customizations;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

//    public Long getVariantId() {
//        return variantId;
//    }
//
//    public void setVariantId(Long variantId) {
//        this.variantId = variantId;
//    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Long getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Long unitPrice) {
        this.unitPrice = unitPrice;
    }
}
