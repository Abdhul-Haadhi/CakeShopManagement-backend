package com.example.CakeShopManagement.repository;

import com.example.CakeShopManagement.entity.OrderItemCustomizationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemCustomizationRepository extends JpaRepository<OrderItemCustomizationEntity, Long> {
}
