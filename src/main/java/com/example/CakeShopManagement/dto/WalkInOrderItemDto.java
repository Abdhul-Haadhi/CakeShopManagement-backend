package com.example.CakeShopManagement.dto;

import java.util.List;

public class WalkInOrderItemDto {
    private Long productId;
    private Long variantId;

    private Long quantity;

    private Long unitPrice;
    private Long subtotal;

    private List<WalkInCustomizationDto> customizations;

    public WalkInOrderItemDto() {
    }

    public WalkInOrderItemDto(Long productId, Long variantId, Long quantity, Long unitPrice, Long subtotal, List<WalkInCustomizationDto> customizations) {
        this.productId = productId;
        this.variantId = variantId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.subtotal = subtotal;
        this.customizations = customizations;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getVariantId() {
        return variantId;
    }

    public void setVariantId(Long variantId) {
        this.variantId = variantId;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public Long getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Long unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Long getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Long subtotal) {
        this.subtotal = subtotal;
    }

    public List<WalkInCustomizationDto> getCustomizations() {
        return customizations;
    }

    public void setCustomizations(List<WalkInCustomizationDto> customizations) {
        this.customizations = customizations;
    }
}
