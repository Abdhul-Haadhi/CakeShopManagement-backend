package com.example.CakeShopManagement.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "product_customizations")
public class ProductCustomizationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductEntity product;

    @ManyToOne
    @JoinColumn(name = "option_id")
    private CustomizationOptionEntity customizationOption;

    @Column(name = "extra_price")
    private BigDecimal extraPrice;




    public ProductCustomizationEntity() {
    }

    public ProductCustomizationEntity(Long id, ProductEntity product, CustomizationOptionEntity customizationOption, BigDecimal extraPrice) {
        this.id = id;
        this.product = product;
        this.customizationOption = customizationOption;
        this.extraPrice = extraPrice;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProductEntity getProduct() {
        return product;
    }

    public void setProduct(ProductEntity product) {
        this.product = product;
    }

    public CustomizationOptionEntity getCustomizationOption() {
        return customizationOption;
    }

    public void setCustomizationOption(CustomizationOptionEntity customizationOption) {
        this.customizationOption = customizationOption;
    }

    public BigDecimal getExtraPrice() {
        return extraPrice;
    }

    public void setExtraPrice(BigDecimal extraPrice) {
        this.extraPrice = extraPrice;
    }
}
