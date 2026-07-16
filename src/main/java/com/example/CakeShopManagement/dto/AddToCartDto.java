package com.example.CakeShopManagement.dto;


import lombok.Data;

import java.util.List;

@Data
public class AddToCartDto {

    private Long productId;

    private Long quantity;

    private String sessionId;

    private List<CartCustomizationDto> customizations;

    private byte[] referenceImage;

    private Long customerId;

    private Long variantId;

    public AddToCartDto() {
    }

    public AddToCartDto(Long productId, Long quantity, String sessionId, List<CartCustomizationDto> customizations, byte[] referenceImage, Long customerId, Long variantId) {
        this.productId = productId;
        this.quantity = quantity;
        this.sessionId = sessionId;
        this.customizations = customizations;
        this.referenceImage = referenceImage;
        this.customerId = customerId;
        this.variantId = variantId;
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

    public byte[] getReferenceImage() {
        return referenceImage;
    }

    public void setReferenceImage(byte[] referenceImage) {
        this.referenceImage = referenceImage;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getVariantId() {
        return variantId;
    }

    public void setVariantId(Long variantId) {
        this.variantId = variantId;
    }
}
