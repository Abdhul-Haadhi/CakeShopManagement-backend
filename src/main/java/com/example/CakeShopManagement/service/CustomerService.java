package com.example.CakeShopManagement.service;

import com.example.CakeShopManagement.dto.CustomerDto;

import java.util.List;

public interface CustomerService {

    CustomerDto addCustomer(CustomerDto customerDto);
    List<CustomerDto> getCustomers();
    CustomerDto updateCustomer(Long customerId, CustomerDto customerDto);
    boolean deleteCustomer(Long customerId);
}
