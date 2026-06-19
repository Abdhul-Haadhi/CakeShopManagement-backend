package com.example.CakeShopManagement.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "order_items")
public class OrderItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderItemId;

    private Long quantity;

    private Long price;

//    @ManyToOne
//    @JoinColumn(name = "order_id")
//    private OrderEntity order;

    @ManyToOne
    @JoinColumn(name = "order_id")
    @JsonIgnore
    private OrderEntity order;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductEntity product;

    @OneToMany(
            mappedBy = "orderItem",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<OrderItemCustomizationEntity> customizations = new ArrayList<>();


    public OrderItemEntity() {
    }

    public OrderItemEntity(Long orderItemId, Long quantity, Long price, OrderEntity order, ProductEntity product, List<OrderItemCustomizationEntity> customizations) {
        this.orderItemId = orderItemId;
        this.quantity = quantity;
        this.price = price;
        this.order = order;
        this.product = product;
        this.customizations = customizations;
    }

    public Long getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(Long orderItemId) {
        this.orderItemId = orderItemId;
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

    public OrderEntity getOrder() {
        return order;
    }

    public void setOrder(OrderEntity order) {
        this.order = order;
    }

    public ProductEntity getProduct() {
        return product;
    }

    public void setProduct(ProductEntity product) {
        this.product = product;
    }

    public List<OrderItemCustomizationEntity> getCustomizations() {
        return customizations;
    }

    public void setCustomizations(List<OrderItemCustomizationEntity> customizations) {
        this.customizations = customizations;
    }
}
