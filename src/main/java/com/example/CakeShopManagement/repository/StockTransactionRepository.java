package com.example.CakeShopManagement.repository;

import com.example.CakeShopManagement.dto.StockTransactionReportDto;
import com.example.CakeShopManagement.entity.StockTransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockTransactionRepository extends JpaRepository<StockTransactionEntity,Long> {

    List<StockTransactionEntity> findByStockStockIdOrderByTransactionDateDesc(Long stockId);
    List<StockTransactionEntity> findByStockInventoryInventoryIdOrderByTransactionDateDesc(Long inventoryId);

    @Query("""
    select new com.example.CakeShopManagement.dto.StockTransactionReportDto(
        t.transactionId,
        t.transactionDate,
        s.inventory.itemSku,
        s.inventory.itemName,
        s.batchNumber,
        t.transactionType,
        t.quantity,
        s.remainingQuantity,
        'System'
    )
    FROM StockTransactionEntity t
    JOIN t.stock s
    ORDER BY t.transactionDate DESC, t.transactionId DESC
    """)
    List<StockTransactionReportDto> getTransactionReport();

}
