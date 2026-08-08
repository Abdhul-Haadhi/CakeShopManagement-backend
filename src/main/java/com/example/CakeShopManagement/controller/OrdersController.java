package com.example.CakeShopManagement.controller;

import com.example.CakeShopManagement.dto.OrderHistoryDto;
import com.example.CakeShopManagement.dto.SalesReportDto;
import com.example.CakeShopManagement.dto.WalkInOrderDto;
import com.example.CakeShopManagement.service.OrderService;
import org.springframework.data.domain.jaxb.SpringDataJaxb;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/employee")
public class OrdersController {

    private final OrderService orderService;

    public OrdersController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/create-walk-in-order")
    public ResponseEntity<WalkInOrderDto> createWalkInOrder(@RequestBody WalkInOrderDto walkInOrderDto) {
        return ResponseEntity.ok(orderService.createWalkInOrder(walkInOrderDto));
    }

    @GetMapping("/get-orders")
    public ResponseEntity<List<OrderHistoryDto>> getAllOrders(){
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @GetMapping("/sales-report")
    public ResponseEntity<SalesReportDto> getSalesReport(
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate)
    {
        return ResponseEntity.ok(orderService.getSalesReport(startDate,endDate));
    }

    @PutMapping("/update-order/{orderId}/{status}")
    public ResponseEntity<OrderHistoryDto> updateOrderStatus(@PathVariable Long orderId, @PathVariable String status){
        return ResponseEntity.ok(orderService.updateOrderStatus(orderId, status));
    }
}
