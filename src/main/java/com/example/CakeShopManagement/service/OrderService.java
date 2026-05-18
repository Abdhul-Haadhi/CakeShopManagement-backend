package com.example.CakeShopManagement.service;

import com.example.CakeShopManagement.dto.PlaceOrderDto;
import com.example.CakeShopManagement.entity.OrderEntity;

public interface OrderService {
    PlaceOrderDto placeOrder(PlaceOrderDto placeOrderDto);
}
