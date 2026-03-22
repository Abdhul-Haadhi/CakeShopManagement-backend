package com.example.CakeShopManagement.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "session")
public class SessionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long sessionId;
    private LocalDateTime loginTime;
    private LocalDateTime logoutTime;
    private Boolean isActive;
}
