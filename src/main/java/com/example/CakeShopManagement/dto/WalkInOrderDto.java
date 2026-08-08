package com.example.CakeShopManagement.dto;

import java.time.LocalDate;
import java.util.List;

public class WalkInOrderDto {
    private String customerName;
    private String phone;
    private LocalDate deliveryDate;
    private String orderType;
    private String paymentMethod;
    private Long paymentId;
    private String notes;
    private Long totalAmount;
    private Long quantity;
    private List<WalkInOrderItemDto> items;

    public WalkInOrderDto() {
    }

    public WalkInOrderDto(String customerName, String phone, LocalDate deliveryDate, String orderType, String paymentMethod, Long paymentId, String notes, Long totalAmount, Long quantity, List<WalkInOrderItemDto> items) {
        this.customerName = customerName;
        this.phone = phone;
        this.deliveryDate = deliveryDate;
        this.orderType = orderType;
        this.paymentMethod = paymentMethod;
        this.paymentId = paymentId;
        this.notes = notes;
        this.totalAmount = totalAmount;
        this.quantity = quantity;
        this.items = items;
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

    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(LocalDate deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Long getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Long paymentId) {
        this.paymentId = paymentId;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
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

    public List<WalkInOrderItemDto> getItems() {
        return items;
    }

    public void setItems(List<WalkInOrderItemDto> items) {
        this.items = items;
    }
}
