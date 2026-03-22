package com.example.CakeShopManagement.entity;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "role")
public class RoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long roleId;
    private String roleName;
}
