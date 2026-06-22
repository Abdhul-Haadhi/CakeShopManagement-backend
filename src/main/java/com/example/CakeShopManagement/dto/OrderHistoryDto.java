package com.example.CakeShopManagement.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class OrderHistoryDto {
    private Long orderId;
    private Long totalAmount;
    private Long quantity;
    private String status;
    private String trackingId;
    private Date orderDate;
    private Date deliveryDate;

    private List<OrderItemDto> orderItems;


//    customer details  //

    private String customerName;
    private String phone;
    private String email;
    private String address;
    private String city;
    private String paymentMethod;



    public OrderHistoryDto() {
    }

    public OrderHistoryDto(Long orderId, Long totalAmount, Long quantity, String status, String trackingId, Date orderDate, Date deliveryDate, List<OrderItemDto> orderItems, String customerName, String phone, String email, String address, String city, String paymentMethod) {
        this.orderId = orderId;
        this.totalAmount = totalAmount;
        this.quantity = quantity;
        this.status = status;
        this.trackingId = trackingId;
        this.orderDate = orderDate;
        this.deliveryDate = deliveryDate;
        this.orderItems = orderItems;
        this.customerName = customerName;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.city = city;
        this.paymentMethod = paymentMethod;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Long totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTrackingId() {
        return trackingId;
    }

    public void setTrackingId(String trackingId) {
        this.trackingId = trackingId;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public List<OrderItemDto> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItemDto> orderItems) {
        this.orderItems = orderItems;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}
