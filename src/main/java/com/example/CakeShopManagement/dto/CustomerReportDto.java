package com.example.CakeShopManagement.dto;

import java.util.Date;

public class CustomerReportDto {

    private String customerName;
    private String phone;
    private Long totalOrders;
    private Long totalSpent;
    private Date lastOrderDate;
    private Date firstOrderDate;
    private String status;

    public CustomerReportDto() {
    }

    public CustomerReportDto(String customerName, String phone, Long totalOrders, Long totalSpent, Date lastOrderDate, Date firstOrderDate, String status) {
        this.customerName = customerName;
        this.phone = phone;
        this.totalOrders = totalOrders;
        this.totalSpent = totalSpent;
        this.lastOrderDate = lastOrderDate;
        this.firstOrderDate = firstOrderDate;
        this.status = status;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Long getTotalOrders() {
        return totalOrders;
    }

    public void setTotalOrders(Long totalOrders) {
        this.totalOrders = totalOrders;
    }

    public Long getTotalSpent() {
        return totalSpent;
    }

    public void setTotalSpent(Long totalSpent) {
        this.totalSpent = totalSpent;
    }

    public Date getLastOrderDate() {
        return lastOrderDate;
    }

    public void setLastOrderDate(Date lastOrderDate) {
        this.lastOrderDate = lastOrderDate;
    }

    public Date getFirstOrderDate() {
        return firstOrderDate;
    }

    public void setFirstOrderDate(Date firstOrderDate) {
        this.firstOrderDate = firstOrderDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
