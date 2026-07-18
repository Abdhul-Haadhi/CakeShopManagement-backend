package com.example.CakeShopManagement.repository;

import com.example.CakeShopManagement.entity.StockEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockRepository extends JpaRepository<StockEntity,Long> {
    boolean existsByInventoryInventoryId(Long inventoryId);
    List<StockEntity> findAllByOrderByReceivedDateDesc();
    List<StockEntity> findByInventoryInventoryIdOrderByReceivedDateDesc(Long inventoryId);
    long countByInventoryInventoryId(Long inventoryId);
    List<StockEntity> findByInventoryInventoryId(Long inventoryId);

    List<StockEntity> findByInventoryInventoryIdAndRemainingQuantityGreaterThanOrderByExpiryDateAscReceivedDateAsc(
            Long inventoryId,
            Double quantity
    );
}
