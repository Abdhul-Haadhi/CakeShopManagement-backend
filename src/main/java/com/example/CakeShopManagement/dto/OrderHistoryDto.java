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

    public OrderHistoryDto() {
    }

    public OrderHistoryDto(Long orderId, Long totalAmount, Long quantity, String status, String trackingId, Date orderDate, Date deliveryDate, List<OrderItemDto> orderItems) {
        this.orderId = orderId;
        this.totalAmount = totalAmount;
        this.quantity = quantity;
        this.status = status;
        this.trackingId = trackingId;
        this.orderDate = orderDate;
        this.deliveryDate = deliveryDate;
        this.orderItems = orderItems;
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
}
