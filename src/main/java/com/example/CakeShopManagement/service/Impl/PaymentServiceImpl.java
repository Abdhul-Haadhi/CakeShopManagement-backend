package com.example.CakeShopManagement.service.Impl;

import com.example.CakeShopManagement.dto.PaymentDto;
import com.example.CakeShopManagement.entity.PaymentEntity;
import com.example.CakeShopManagement.repository.PaymentRepository;
import com.example.CakeShopManagement.service.PaymentService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;

    public PaymentServiceImpl(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Override
    public PaymentEntity processMockPayment(PaymentDto dto){
        if(dto.getCardNumber() == null || dto.getCardNumber().length() != 16){
            throw new RuntimeException("Invalid card number");
        }
        if(dto.getCvv() == null || dto.getCvv().length() != 3){
            throw new RuntimeException("Invalid cvv");
        }

        PaymentEntity payment = new PaymentEntity();

        payment.setPaymentMethod("CARD");
        payment.setPaymentStatus("PAID");
        payment.setTransactionId("DEMO-" + System.currentTimeMillis());
        payment.setGatewayName("Mock Gateway");
        payment.setAmount(dto.getAmount());
        payment.setPaymentDate(LocalDate.now());

        return paymentRepository.save(payment);
    }
}
