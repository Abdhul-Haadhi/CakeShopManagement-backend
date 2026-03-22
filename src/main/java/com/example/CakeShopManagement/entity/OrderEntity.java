package com.example.CakeShopManagement.entity;


import com.example.CakeShopManagement.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
@Table(name = "order_table")
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long orderId;
    private Date orderDate;
    private Date deliveryDate;
    private Double totalAmount;
    private OrderStatus status;
    private String customDescription;

}
