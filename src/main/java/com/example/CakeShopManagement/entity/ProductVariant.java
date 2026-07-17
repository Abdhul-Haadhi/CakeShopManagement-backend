package com.example.CakeShopManagement.entity;

import com.example.CakeShopManagement.enums.VariantType;
import jakarta.persistence.*;

@Entity
@Table(name="product_variant")
public class ProductVariant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long variantId;

    private Integer weight;
    private Integer pieces;

    @Enumerated(EnumType.STRING)
    private VariantType variantType;

    private Double price;
    private Boolean available=true;

    @ManyToOne
    @JoinColumn(name="product_id")
    private ProductEntity product;

    public ProductVariant() {
    }

    public ProductVariant(Long variantId, Integer weight, Integer pieces, VariantType variantType, Double price, Boolean available, ProductEntity product) {
        this.variantId = variantId;
        this.weight = weight;
        this.pieces = pieces;
        this.variantType = variantType;
        this.price = price;
        this.available = available;
        this.product = product;
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

    public VariantType getVariantType() {
        return variantType;
    }

    public void setVariantType(VariantType variantType) {
        this.variantType = variantType;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public ProductEntity getProduct() {
        return product;
    }

    public void setProduct(ProductEntity product) {
        this.product = product;
    }
}
