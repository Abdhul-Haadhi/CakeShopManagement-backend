package com.example.CakeShopManagement.service.auth;

import com.example.CakeShopManagement.dto.CustomerDto;
import com.example.CakeShopManagement.dto.CustomerSignupRequestDto;

public interface CustomerAuthService {
    CustomerDto register(CustomerSignupRequestDto request);
    String sendOtp(String phone);
    CustomerDto verifyOtp(String phone, String otp);
}
