package com.example.CakeShopManagement.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "cart_item_customizations")
public class CartItemCustomizationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private CartItemsEntity cartItem;

    @ManyToOne
    @JoinColumn(name = "option_id")
    private CustomizationOptionEntity option;

    private String selectedValue;

    private BigDecimal extraPrice;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] referenceImage;

    public CartItemCustomizationEntity() {
    }

    public CartItemCustomizationEntity(Long id, CartItemsEntity cartItem, CustomizationOptionEntity option, String selectedValue, BigDecimal extraPrice, byte[] referenceImage) {
        this.id = id;
        this.cartItem = cartItem;
        this.option = option;
        this.selectedValue = selectedValue;
        this.extraPrice = extraPrice;
        this.referenceImage = referenceImage;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CartItemsEntity getCartItem() {
        return cartItem;
    }

    public void setCartItem(CartItemsEntity cartItem) {
        this.cartItem = cartItem;
    }

    public CustomizationOptionEntity getOption() {
        return option;
    }

    public void setOption(CustomizationOptionEntity option) {
        this.option = option;
    }

    public String getSelectedValue() {
        return selectedValue;
    }

    public void setSelectedValue(String selectedValue) {
        this.selectedValue = selectedValue;
    }

    public BigDecimal getExtraPrice() {
        return extraPrice;
    }

    public void setExtraPrice(BigDecimal extraPrice) {
        this.extraPrice = extraPrice;
    }

    public byte[] getReferenceImage() {
        return referenceImage;
    }

    public void setReferenceImage(byte[] referenceImage) {
        this.referenceImage = referenceImage;
    }
}
