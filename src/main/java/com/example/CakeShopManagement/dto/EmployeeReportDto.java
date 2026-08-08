package com.example.CakeShopManagement.dto;

import java.time.LocalDate;

public class EmployeeReportDto {

    private Long employeeId;
    private String employeeName;
    private String email;
    private String roleName;
    private String phone;
    private String status;
    private LocalDate joinDate;

    public EmployeeReportDto() {
    }

    public EmployeeReportDto(Long employeeId, String employeeName, String email, String roleName, String phone, String status, LocalDate joinDate) {
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.email = email;
        this.roleName = roleName;
        this.phone = phone;
        this.status = status;
        this.joinDate = joinDate;
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

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(LocalDate joinDate) {
        this.joinDate = joinDate;
    }
}
