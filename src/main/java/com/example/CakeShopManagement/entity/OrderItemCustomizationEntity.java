package com.example.CakeShopManagement.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
@Table(name = "order_item_customizations")
public class OrderItemCustomizationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_item_id")
    private OrderItemEntity orderItem;

    @ManyToOne
    @JoinColumn(name = "option_id")
    private CustomizationOptionEntity option;

    private String selectedValue;

    private BigDecimal extraPrice;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] referenceImage;

    public OrderItemCustomizationEntity() {
    }

    public OrderItemCustomizationEntity(Long id, OrderItemEntity orderItem, CustomizationOptionEntity option, String selectedValue, BigDecimal extraPrice, byte[] referenceImage) {
        this.id = id;
        this.orderItem = orderItem;
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

    public OrderItemEntity getOrderItem() {
        return orderItem;
    }

    public void setOrderItem(OrderItemEntity orderItem) {
        this.orderItem = orderItem;
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
