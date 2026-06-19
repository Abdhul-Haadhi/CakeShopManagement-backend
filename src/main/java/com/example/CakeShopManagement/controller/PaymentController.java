package com.example.CakeShopManagement.controller;

import com.example.CakeShopManagement.dto.PaymentDto;
import com.example.CakeShopManagement.entity.PaymentEntity;
import com.example.CakeShopManagement.service.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/public/payment")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/process")
    public ResponseEntity<PaymentEntity> processPayment(@RequestBody PaymentDto paymentDto) {
        PaymentEntity payment = paymentService.processMockPayment(paymentDto);
        return ResponseEntity.ok(payment);
    }
}
