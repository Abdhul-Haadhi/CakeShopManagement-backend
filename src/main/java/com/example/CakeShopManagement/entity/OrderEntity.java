package com.example.CakeShopManagement.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name="order_table")
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    private String customerName;
    private String phone;
    private String email;

    private String address;
    private String city;

    private Date orderDate;
    private Date deliveryDate;

    private Long totalAmount;
    private Long quantity;

    private String paymentMethod;

    private String trackingId;

    private String status;

    @OneToMany(mappedBy = "order")
    private List<CartItemsEntity> cartItems;

    @OneToMany(mappedBy = "order",cascade = CascadeType.ALL)
    private List<OrderItemEntity> orderItems;


    public OrderEntity() {
    }

    public OrderEntity(Long orderId, String customerName, String phone, String email, String address, String city, Date orderDate, Date deliveryDate, Long totalAmount, Long quantity, String paymentMethod, String trackingId, String status, List<CartItemsEntity> cartItems, List<OrderItemEntity> orderItems) {
        this.orderId = orderId;
        this.customerName = customerName;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.city = city;
        this.orderDate = orderDate;
        this.deliveryDate = deliveryDate;
        this.totalAmount = totalAmount;
        this.quantity = quantity;
        this.paymentMethod = paymentMethod;
        this.trackingId = trackingId;
        this.status = status;
        this.cartItems = cartItems;
        this.orderItems = orderItems;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
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

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getTrackingId() {
        return trackingId;
    }

    public void setTrackingId(String trackingId) {
        this.trackingId = trackingId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<CartItemsEntity> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItemsEntity> cartItems) {
        this.cartItems = cartItems;
    }

    public List<OrderItemEntity> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItemEntity> orderItems) {
        this.orderItems = orderItems;
    }
}
