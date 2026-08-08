package com.example.CakeShopManagement.service;

import com.example.CakeShopManagement.dto.OrderHistoryDto;
import com.example.CakeShopManagement.dto.PlaceOrderDto;
import com.example.CakeShopManagement.dto.SalesReportDto;
import com.example.CakeShopManagement.entity.OrderEntity;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface OrderService {
    PlaceOrderDto placeOrder(PlaceOrderDto placeOrderDto);
//    List<OrderEntity> getOrdersBySession(String sessionId);
    List<OrderHistoryDto> getOrdersBySession(String sessionId);
    List<OrderHistoryDto> getOrders(Long customerId, String sessionId);
    List<OrderHistoryDto> getAllOrders();
    OrderHistoryDto updateOrderStatus(Long orderId, String status);

    SalesReportDto getSalesReport(LocalDate startDate, LocalDate endDate);

    OrderHistoryDto cancelOrder(Long orderId);
}
