package com.example.CakeShopManagement.dto;

import lombok.Data;

@Data
public class UpdateCartQuantityDto {

    private Long quantity;

    public UpdateCartQuantityDto() {
    }

    public UpdateCartQuantityDto(Long quantity) {
        this.quantity = quantity;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }
}
