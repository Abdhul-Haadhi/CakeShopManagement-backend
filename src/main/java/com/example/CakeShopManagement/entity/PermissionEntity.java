package com.example.CakeShopManagement.entity;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "permission")
public class PermissionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long permissionId;
    private String permissionName;
    private String description;
}
