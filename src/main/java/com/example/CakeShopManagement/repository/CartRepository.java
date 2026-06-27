package com.example.CakeShopManagement.repository;


import com.example.CakeShopManagement.entity.CartItemsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<CartItemsEntity, Long> {

    List<CartItemsEntity> findByCustomerCustomerIdAndOrderIsNull(Long customerId);
    List<CartItemsEntity> findBySessionIdAndOrderIsNull(String sessionId);
    List<CartItemsEntity> findBySessionIdAndCustomerIsNullAndOrderIsNull(String sessionId);
}
