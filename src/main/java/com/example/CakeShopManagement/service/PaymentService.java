package com.example.CakeShopManagement.service;

import com.example.CakeShopManagement.dto.PaymentDto;
import com.example.CakeShopManagement.entity.PaymentEntity;

public interface PaymentService {
    PaymentEntity processMockPayment(PaymentDto dto);
}
