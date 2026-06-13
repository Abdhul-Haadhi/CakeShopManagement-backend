package com.example.CakeShopManagement.repository;

import com.example.CakeShopManagement.entity.CartItemCustomizationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemCustomizationRepository extends JpaRepository<CartItemCustomizationEntity, Long> {

}
