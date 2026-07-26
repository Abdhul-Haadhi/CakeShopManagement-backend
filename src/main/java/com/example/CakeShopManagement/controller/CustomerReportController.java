package com.example.CakeShopManagement.controller;

import com.example.CakeShopManagement.dto.CustomerReportDto;
import com.example.CakeShopManagement.service.CustomerReportService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class CustomerReportController {

    private final CustomerReportService customerReportService;

    public CustomerReportController(CustomerReportService customerReportService) {
        this.customerReportService = customerReportService;
    }

    @GetMapping("/customer-report")
    public ResponseEntity<List<CustomerReportDto>> getCustomerReport() {
        return ResponseEntity.ok(customerReportService.getCustomerReport());
    }
}
