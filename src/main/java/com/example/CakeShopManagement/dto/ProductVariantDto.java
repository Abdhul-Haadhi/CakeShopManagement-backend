package com.example.CakeShopManagement.dto;

import lombok.Data;

@Data
public class ProductVariantDto {

    private Long variantId;
    private Integer weight;
    private Integer pieces;
    private String variantType;
    private Double price;

    public ProductVariantDto() {
    }

    public ProductVariantDto(Long variantId, Integer weight, Integer pieces, String variantType, Double price) {
        this.variantId = variantId;
        this.weight = weight;
        this.pieces = pieces;
        this.variantType = variantType;
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

    public Integer getPieces() {
        return pieces;
    }

    public void setPieces(Integer pieces) {
        this.pieces = pieces;
    }

    public String getVariantType() {
        return variantType;
    }

    public void setVariantType(String variantType) {
        this.variantType = variantType;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
