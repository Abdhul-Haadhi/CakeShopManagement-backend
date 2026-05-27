package com.example.CakeShopManagement.service;

import com.example.CakeShopManagement.dto.PlaceOrderDto;
import com.example.CakeShopManagement.entity.OrderEntity;

import java.util.List;

public interface OrderService {
    PlaceOrderDto placeOrder(PlaceOrderDto placeOrderDto);
    List<OrderEntity> getOrdersBySession(String sessionId);
}
