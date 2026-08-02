package com.example.CakeShopManagement.dto;

import java.util.List;

public class RecipeDetailsDto {
    private Long productId;
    private String productName;

    private Long variantId;
    private String variantType;
    private Integer weight;
    private Integer pieces;

    private List<RecipeDto> ingredients;


    public RecipeDetailsDto() {
    }

    public RecipeDetailsDto(Long productId, String productName, Long variantId, String variantType, Integer weight, Integer pieces, List<RecipeDto> ingredients) {
        this.productId = productId;
        this.productName = productName;
        this.variantId = variantId;
        this.variantType = variantType;
        this.weight = weight;
        this.pieces = pieces;
        this.ingredients = ingredients;
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

    public Long getVariantId() {
        return variantId;
    }

    public void setVariantId(Long variantId) {
        this.variantId = variantId;
    }

    public String getVariantType() {
        return variantType;
    }

    public void setVariantType(String variantType) {
        this.variantType = variantType;
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

    public List<RecipeDto> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<RecipeDto> ingredients) {
        this.ingredients = ingredients;
    }
}
