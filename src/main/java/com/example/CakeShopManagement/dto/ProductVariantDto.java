package com.example.CakeShopManagement.dto;

import lombok.Data;

@Data
public class ProductVariantDto {

    private Long variantId;
    private Integer weight;
    private Double price;

    public ProductVariantDto() {
    }

    public ProductVariantDto(Long variantId, Integer weight, Double price) {
        this.variantId = variantId;
        this.weight = weight;
        this.price = price;
    }

    public Long getVariantId() {
        return variantId;
    }

    public void setVariantId(Long variantId) {
        this.variantId = variantId;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
