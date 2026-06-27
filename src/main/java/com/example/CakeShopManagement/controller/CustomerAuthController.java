package com.example.CakeShopManagement.controller;

import com.example.CakeShopManagement.dto.CustomerDto;
import com.example.CakeShopManagement.dto.CustomerSignupRequestDto;
import com.example.CakeShopManagement.dto.OtpRequestDto;
import com.example.CakeShopManagement.dto.OtpVerifyDto;
import com.example.CakeShopManagement.exceptions.AppException;
import com.example.CakeShopManagement.service.CustomerService;
import com.example.CakeShopManagement.service.auth.CustomerAuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/customer/auth")
public class CustomerAuthController {

    private final CustomerAuthService customerAuthService;
    private final CustomerService customerService;

    public CustomerAuthController(CustomerAuthService customerAuthService, CustomerService customerService) {
        this.customerAuthService = customerAuthService;
        this.customerService = customerService;
    }

    @PostMapping("/register")
    public ResponseEntity<CustomerDto> registerCustomer(@RequestBody CustomerSignupRequestDto requestDto) {

        CustomerDto customerDto = customerAuthService.register(requestDto);

        return ResponseEntity.ok(customerDto);
    }

    @PostMapping("/send-otp")
    public ResponseEntity<?> sendOtp(@RequestBody OtpRequestDto request) {

        String otp = customerAuthService.sendOtp(request.getPhone());

        Map<String, String> response = new HashMap<>();
        response.put("message","OTP generated successfully");
        response.put("otp",otp);
        System.out.println("***************hello****"+otp);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<CustomerDto> verifyOtp(@RequestBody OtpVerifyDto request) {
        CustomerDto customer = customerAuthService.verifyOtp(request.getPhone(), request.getOtp());

        return ResponseEntity.ok(customer);
    }

    @GetMapping("/get-customer/{customerId}")
    public ResponseEntity<CustomerDto> getCustomerById(@PathVariable Long customerId){
        try {
            return ResponseEntity.ok(customerService.getCustomerById(customerId));
        }
        catch (Exception e) {
            throw new AppException("Request failed with error: " + e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update-customer/{customerId}")
    public ResponseEntity<CustomerDto> updateCustomer(@PathVariable Long customerId, @RequestBody CustomerDto customerDto){
        return ResponseEntity.ok(customerService.updateCustomer(customerId,customerDto));
    }

}
