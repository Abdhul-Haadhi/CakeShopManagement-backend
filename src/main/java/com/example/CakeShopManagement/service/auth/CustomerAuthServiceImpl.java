package com.example.CakeShopManagement.service.auth;

import com.example.CakeShopManagement.dto.CustomerDto;
import com.example.CakeShopManagement.dto.CustomerSignupRequestDto;
import com.example.CakeShopManagement.entity.CustomerEntity;
import com.example.CakeShopManagement.exceptions.AppException;
import com.example.CakeShopManagement.mappers.CustomerMapper;
import com.example.CakeShopManagement.repository.CustomerRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class CustomerAuthServiceImpl implements CustomerAuthService {

    private final CustomerMapper customerMapper;
    private final CustomerRepository customerRepository;

    public CustomerAuthServiceImpl(CustomerMapper customerMapper, CustomerRepository customerRepository) {
        this.customerMapper = customerMapper;
        this.customerRepository = customerRepository;
    }


    public CustomerDto register(CustomerSignupRequestDto request){
        CustomerEntity customer = new CustomerEntity();

        if(customerRepository.findByPhone(request.getPhone()).isPresent()){
            throw new AppException("User already registered", HttpStatus.BAD_REQUEST);
        }

        if(customerRepository.findByEmail(request.getEmail()).isPresent()){
            throw new AppException("User already registered", HttpStatus.BAD_REQUEST);
        }

        customer.setCustomerName(request.getCustomerName());
        customer.setPhone(request.getPhone());
        customer.setEmail(request.getEmail());
        customer.setAddress(request.getAddress());
        customer.setJoinDate(LocalDate.now());

        return customerMapper.toCustomerDto(customerRepository.save(customer));
    }

    public String sendOtp(String phone){

        CustomerEntity customer = customerRepository.findByPhone(phone)
                .orElseThrow(()->new RuntimeException("Customer Not Found"));

        String otp = String.valueOf((int) (Math.random() * 900000) + 100000);

        customer.setOtp(otp);
        customer.setOptExpiry(LocalDateTime.now().now().plusMinutes(5));

        customerRepository.save(customer);

        return otp;
    }

    public CustomerDto verifyOtp(String phone, String otp){

        CustomerEntity customer = customerRepository.findByPhone(phone).orElseThrow(()->new RuntimeException("Customer Not Found"));

        if(customer.getOtp().equals(otp) &&customer.getOptExpiry().isAfter(LocalDateTime.now())){
            customer.setVerified(true);
            customer.setOtp(null);
            customer.setOptExpiry(null);

            customerRepository.save(customer);

            return customerMapper.toCustomerDto(customer);
        }
        throw new RuntimeException("Invalid OTP");
    }
}
