package com.example.CakeShopManagement.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "employee")
public class EmployeeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long employeeId;
    private String employeeName;
    @Column(nullable = false, unique = true)
    private String email;
    private String phone;
    private String address;
    private LocalDateTime joinDate;
    private String password;

    public EmployeeEntity() {
    }

    public EmployeeEntity(Long employeeId, String employeeName, String email, String phone, String address, LocalDateTime joinDate, String password) {
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.joinDate = joinDate;
        this.password = password;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDateTime getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(LocalDateTime joinDate) {
        this.joinDate = joinDate;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
