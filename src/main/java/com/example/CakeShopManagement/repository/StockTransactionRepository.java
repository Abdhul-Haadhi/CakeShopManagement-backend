package com.example.CakeShopManagement.repository;

import com.example.CakeShopManagement.entity.StockTransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockTransactionRepository extends JpaRepository<StockTransactionEntity,Long> {

    List<StockTransactionEntity> findByStockStockIdOrderByTransactionDateDesc(Long stockId);
    List<StockTransactionEntity> findByStockInventoryInventoryIdOrderByTransactionDateDesc(Long inventoryId);

}
