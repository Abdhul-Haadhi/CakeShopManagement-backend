package com.example.CakeShopManagement.entity;


import com.example.CakeShopManagement.dto.CartCustomizationDto;
import com.example.CakeShopManagement.dto.CartItemDto;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "cart")
public class CartItemsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartId;

    private String sessionId;

    private Long price;

    private Long quantity;


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id",nullable = false)
    private ProductEntity productEntity;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private OrderEntity order;

    @OneToMany(mappedBy = "cartItem", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItemCustomizationEntity> customizations = new ArrayList<>();


    public CartItemDto getDto(){
        CartItemDto dto = new CartItemDto();

        dto.setCustomizations(
                customizations.stream()
                        .map(c->{
                            CartCustomizationDto cdto = new CartCustomizationDto();

                            cdto.setOptionId(c.getOption().getOptionId());
                            cdto.setOptionName(c.getOption().getOptionName());
                            cdto.setValue(c.getSelectedValue());
                            cdto.setExtraPrice(c.getExtraPrice().longValue());

                            return cdto;
                        }).toList()
        );

        dto.setCartId(cartId);
        dto.setSessionId(sessionId);
        dto.setQuantity(quantity);
        dto.setPrice(price);

        dto.setProductId(productEntity.getProductId());
        dto.setProductName(productEntity.getProductName());
        dto.setByteImage(productEntity.getImage());

        return dto;
    }


    public CartItemsEntity() {
    }

    public CartItemsEntity(Long cartId, String sessionId, Long price, Long quantity, ProductEntity productEntity, OrderEntity order, List<CartItemCustomizationEntity> customizations) {
        this.cartId = cartId;
        this.sessionId = sessionId;
        this.price = price;
        this.quantity = quantity;
        this.productEntity = productEntity;
        this.order = order;
        this.customizations = customizations;
    }

    public Long getCartId() {
        return cartId;
    }

    public void setCartId(Long cartId) {
        this.cartId = cartId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public ProductEntity getProductEntity() {
        return productEntity;
    }

    public void setProductEntity(ProductEntity productEntity) {
        this.productEntity = productEntity;
    }

    public OrderEntity getOrder() {
        return order;
    }

    public void setOrder(OrderEntity order) {
        this.order = order;
    }

    public List<CartItemCustomizationEntity> getCustomizations() {
        return customizations;
    }

    public void setCustomizations(List<CartItemCustomizationEntity> customizations) {
        this.customizations = customizations;
    }
}
