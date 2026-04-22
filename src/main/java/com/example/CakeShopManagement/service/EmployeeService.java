package com.example.CakeShopManagement.service;

import com.example.CakeShopManagement.dto.EmployeeDto;

import java.util.List;

public interface EmployeeService {
    EmployeeDto addEmployee(EmployeeDto employeeDto);
    List<EmployeeDto> getEmployees();
    EmployeeDto updateEmployee(Long employeeId, EmployeeDto employeeDto);
//    EmployeeDto deleteEmployee(long employeeId);
    boolean deleteEmployee(Long employeeId);
}
