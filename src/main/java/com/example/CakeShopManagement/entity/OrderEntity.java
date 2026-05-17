//package com.example.CakeShopManagement.entity;
//
//
//import com.example.CakeShopManagement.enums.OrderStatus;
//import jakarta.persistence.*;
//import lombok.Data;
//
//import java.util.Date;
//import java.util.List;
//import java.util.UUID;
//
//@Entity
//@Data
//@Table(name = "order_table")
//public class OrderEntity {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private long orderId;
//    private String orderDescription;
//    private Date orderDate;
//    private Long amount;
//    private String address;
//    private String payment;
//    private OrderStatus orderStatus;
//    private Long totalAmount;
//    private Long discount;
//    private UUID trackingId;
//
////    @OneToOne(cascade = CascadeType.MERGE)
////    @JoinColumn(name = "user_id",referencedColumnName = "userId")
////    private UserEntity userEntity;
////
////
////    @OneToMany(fetch = FetchType.LAZY,mappedBy = "orderEntity")
////    private List<CartItemsEntity> cartItemsEntities;
////
////
////    public OrderEntity() {
////    }
////
////    public OrderEntity(long orderId, String orderDescription, Date orderDate, Long amount, String address, String payment, OrderStatus orderStatus, Long totalAmount, Long discount, UUID trackingId, UserEntity userEntity, List<CartItemsEntity> cartItemsEntities) {
////        this.orderId = orderId;
////        this.orderDescription = orderDescription;
////        this.orderDate = orderDate;
////        this.amount = amount;
////        this.address = address;
////        this.payment = payment;
////        this.orderStatus = orderStatus;
////        this.totalAmount = totalAmount;
////        this.discount = discount;
////        this.trackingId = trackingId;
////        this.userEntity = userEntity;
////        this.cartItemsEntities = cartItemsEntities;
////    }
////
////
////    public long getOrderId() {
////        return orderId;
////    }
////
////    public void setOrderId(long orderId) {
////        this.orderId = orderId;
////    }
////
////    public String getOrderDescription() {
////        return orderDescription;
////    }
////
////    public void setOrderDescription(String orderDescription) {
////        this.orderDescription = orderDescription;
////    }
////
////    public Date getOrderDate() {
////        return orderDate;
////    }
////
////    public void setOrderDate(Date orderDate) {
////        this.orderDate = orderDate;
////    }
////
////    public Long getAmount() {
////        return amount;
////    }
////
////    public void setAmount(Long amount) {
////        this.amount = amount;
////    }
////
////    public String getAddress() {
////        return address;
////    }
////
////    public void setAddress(String address) {
////        this.address = address;
////    }
////
////    public String getPayment() {
////        return payment;
////    }
////
////    public void setPayment(String payment) {
////        this.payment = payment;
////    }
////
////    public OrderStatus getOrderStatus() {
////        return orderStatus;
////    }
////
////    public void setOrderStatus(OrderStatus orderStatus) {
////        this.orderStatus = orderStatus;
////    }
////
////    public Long getTotalAmount() {
////        return totalAmount;
////    }
////
////    public void setTotalAmount(Long totalAmount) {
////        this.totalAmount = totalAmount;
////    }
////
////    public Long getDiscount() {
////        return discount;
////    }
////
////    public void setDiscount(Long discount) {
////        this.discount = discount;
////    }
////
////    public UUID getTrackingId() {
////        return trackingId;
////    }
////
////    public void setTrackingId(UUID trackingId) {
////        this.trackingId = trackingId;
////    }
////
////    public UserEntity getUserEntity() {
////        return userEntity;
////    }
////
////    public void setUserEntity(UserEntity userEntity) {
////        this.userEntity = userEntity;
////    }
////
////    public List<CartItemsEntity> getCartItemsEntities() {
////        return cartItemsEntities;
////    }
////
////    public void setCartItemsEntities(List<CartItemsEntity> cartItemsEntities) {
////        this.cartItemsEntities = cartItemsEntities;
////    }
//}
