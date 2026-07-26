package com.example.CakeShopManagement.service.Impl;

import com.example.CakeShopManagement.dto.CustomerReportDto;
import com.example.CakeShopManagement.repository.CustomerReportRepository;
import com.example.CakeShopManagement.service.CustomerReportService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

@Service
public class CustomerReportServiceImpl implements CustomerReportService {

    private final CustomerReportRepository repository;

    public CustomerReportServiceImpl(CustomerReportRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<CustomerReportDto> getCustomerReport(){
        System.out.println("Customer report service called");
        List<CustomerReportDto> report = repository.getCustomerReport();

        System.out.println("Records = " + report.size());

        LocalDate today = LocalDate.now();

        for(CustomerReportDto customer: report){
            LocalDate lastOrder = customer.getLastOrderDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            if(customer.getTotalSpent() >= 40000){
                customer.setStatus("Regular");
            } else if (lastOrder.isAfter(today.minusDays(30))) {
                customer.setStatus("Active");
            }
            else {
                customer.setStatus("Inactive");
            }
        }
        return report;
    }
}
