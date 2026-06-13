package com.example.CakeShopManagement.controller;


import com.example.CakeShopManagement.dto.PlaceOrderDto;
import com.example.CakeShopManagement.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/public")
public class PlaceOrderController {

    private final OrderService orderService;

    public PlaceOrderController(OrderService orderService) {
        this.orderService = orderService;
    }


    @PostMapping("/place-order")
    public ResponseEntity<?> placeOrder(@RequestBody PlaceOrderDto placeOrderDto){
        return ResponseEntity.ok(orderService.placeOrder(placeOrderDto));
    }

//    @GetMapping("/orders/{sessionId}")
//    public ResponseEntity<?> getOrder(@PathVariable String sessionId){
//        return ResponseEntity.ok(orderService.getOrdersBySession(sessionId));
//    }

    @GetMapping("/orders/{sessionId}")
    public ResponseEntity<?> getOrder(@PathVariable String sessionId){

        var data = orderService.getOrdersBySession(sessionId);

        System.out.println(data);

        return ResponseEntity.ok(data);
    }

}
