package com.example.CakeShopManagement.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class PlaceOrderDto {
    private String customerName;
    private String phone;
    private String email;
    private String address;
    private String city;
    private Date deliveryDate;
    private String notes;
    private String paymentMethod;

    private Long totalAmount;
    private Long quantity;
    private String sessionId;

    private List<Long> cartItemIds;

    private PaymentDto payment;

    private Long paymentId;

    private Long customerId;

    public PlaceOrderDto() {
    }

    public PlaceOrderDto(String customerName, String phone, String email, String address, String city, Date deliveryDate, String notes, String paymentMethod, Long totalAmount, Long quantity, String sessionId, List<Long> cartItemIds, PaymentDto payment, Long paymentId, Long customerId) {
        this.customerName = customerName;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.city = city;
        this.deliveryDate = deliveryDate;
        this.notes = notes;
        this.paymentMethod = paymentMethod;
        this.totalAmount = totalAmount;
        this.quantity = quantity;
        this.sessionId = sessionId;
        this.cartItemIds = cartItemIds;
        this.payment = payment;
        this.paymentId = paymentId;
        this.customerId = customerId;
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

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
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

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public List<Long> getCartItemIds() {
        return cartItemIds;
    }

    public void setCartItemIds(List<Long> cartItemIds) {
        this.cartItemIds = cartItemIds;
    }

    public PaymentDto getPayment() {
        return payment;
    }

    public void setPayment(PaymentDto payment) {
        this.payment = payment;
    }

    public Long getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Long paymentId) {
        this.paymentId = paymentId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }
}
