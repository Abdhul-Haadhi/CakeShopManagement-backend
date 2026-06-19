package com.example.CakeShopManagement.controller;

import com.example.CakeShopManagement.dto.OrderHistoryDto;
import com.example.CakeShopManagement.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employee")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/get-orders")
    public ResponseEntity<List<OrderHistoryDto>> getAllOrders(){
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @PutMapping("/update-order/{orderId}/{status}")
    public ResponseEntity<OrderHistoryDto> updateOrderStatus(@PathVariable Long orderId, @PathVariable String status){
        return ResponseEntity.ok(orderService.updateOrderStatus(orderId, status));
    }
}
