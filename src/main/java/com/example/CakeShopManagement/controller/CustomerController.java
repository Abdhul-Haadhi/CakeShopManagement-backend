package com.example.CakeShopManagement.controller;


import com.example.CakeShopManagement.dto.CustomerDto;
import com.example.CakeShopManagement.exceptions.AppException;
import com.example.CakeShopManagement.service.CustomerService;
import com.example.CakeShopManagement.service.Impl.CustomerServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/employee")
public class CustomerController {

    private final CustomerService customerService;
    private final CustomerServiceImpl customerServiceImpl;

    public CustomerController(CustomerService customerService, CustomerServiceImpl customerServiceImpl) {
        this.customerService = customerService;
        this.customerServiceImpl = customerServiceImpl;
    }

    @PostMapping("/customer")
    public ResponseEntity<CustomerDto> addForm(@RequestBody CustomerDto customerDto) {

        try {
            CustomerDto customerDtoResponse = customerService.addCustomer(customerDto);
            return ResponseEntity.created(URI.create("/customer"+customerDtoResponse.getCustomerId())).body(customerDtoResponse);
        }
        catch (Exception e) {
            throw new AppException("Request failed with error: " + e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/customer")
    public ResponseEntity<List<CustomerDto>> getCustomers(){
        try {
            List<CustomerDto> customerDtoList = customerService.getCustomers();
            return ResponseEntity.ok(customerDtoList);
        }
        catch (Exception e) {
            throw new AppException("Request failed with error: " + e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/customer/{customerId}")
    public ResponseEntity<CustomerDto> updateCustomer(@PathVariable Long customerId, @RequestBody CustomerDto customerDto) {
        try {
            CustomerDto customerDto1 = customerService.updateCustomer(customerId, customerDto);
            return ResponseEntity.ok(customerDto1);
        }
        catch (Exception e) {
            throw new AppException("Request failed with error: " + e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("customer/{customerId}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long customerId) {

        boolean deleted = customerService.deleteCustomer(customerId);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
