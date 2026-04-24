package com.example.CakeShopManagement.controller;

import com.example.CakeShopManagement.dto.EmployeeDto;
import com.example.CakeShopManagement.exceptions.AppException;
import com.example.CakeShopManagement.service.EmployeeService;
import com.example.CakeShopManagement.service.Impl.EmployeeServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final EmployeeServiceImpl employeeServiceImpl;

    public EmployeeController(EmployeeService employeeService, EmployeeServiceImpl employeeServiceImpl) {
        this.employeeService = employeeService;
        this.employeeServiceImpl = employeeServiceImpl;
    }

//    @PostMapping("/employee")
//    public ResponseEntity<EmployeeDto> addEmployee(@RequestBody EmployeeDto employeeDto) {
//        try {
//            EmployeeDto employeeDto1 = employeeService.addEmployee(employeeDto);
//            return ResponseEntity.status(HttpStatus.CREATED).body(employeeDto1);
//        }
//        catch (Exception e){
//            throw new AppException("Request failed with error: " + e, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

    @PostMapping("/employee")
    public ResponseEntity<EmployeeDto> addForm(@RequestBody EmployeeDto employeeDto) {

        try {
            EmployeeDto employeeDtoResponse = employeeService.addEmployee(employeeDto);
            return ResponseEntity.created(URI.create("/employee"+employeeDtoResponse.getEmployeeId())).body(employeeDtoResponse);
        }
        catch (Exception e) {
            throw new AppException("Request failed with error: " + e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/employee-login")
    public ResponseEntity<?> createEmployeeLogin(@RequestBody EmployeeDto employeeDto) {
        try {
            employeeServiceImpl.createEmployeeLogin(employeeDto);
            return ResponseEntity.ok("Employee account already exists");
        }
        catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @GetMapping("/employee")
    public ResponseEntity<List<EmployeeDto>> getEmployees(){
        try {
            List<EmployeeDto> employeeDtoList = employeeService.getEmployees();
            return ResponseEntity.ok(employeeDtoList);
        }
        catch (Exception e) {
            throw new AppException("Request failed with error: " + e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/employee/{employeeId}")
    public ResponseEntity<EmployeeDto> updateEmployee(@PathVariable Long employeeId, @RequestBody EmployeeDto employeeDto) {
        try {
            EmployeeDto employeeDto1 = employeeService.updateEmployee(employeeId, employeeDto);
            return ResponseEntity.ok(employeeDto1);
        }
        catch (Exception e) {
            throw new AppException("Request failed with error: " + e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("employee/{employeeId}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long employeeId) {
//        try {
//            EmployeeDto employeeDto = employeeService.deleteEmployee(employeeId);
//            return ResponseEntity.ok(employeeDto);
//        }
//        catch (Exception e) {
//            throw new AppException("Request failed with error: " + e, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
        boolean deleted = employeeService.deleteEmployee(employeeId);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}
