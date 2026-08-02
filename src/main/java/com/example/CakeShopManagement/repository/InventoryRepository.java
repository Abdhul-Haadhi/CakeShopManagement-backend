package com.example.CakeShopManagement.repository;

import com.example.CakeShopManagement.dto.InventoryReportDto;
import com.example.CakeShopManagement.dto.InventorySummaryReportDto;
import com.example.CakeShopManagement.entity.InventoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InventoryRepository extends JpaRepository<InventoryEntity,Long> {

    Optional<InventoryEntity> findByItemName(String itemName);
    Boolean existsByItemSku(String itemSku);

    @Query("""
    SELECT new com.example.CakeShopManagement.dto.InventoryReportDto(
        i.itemSku,
        i.itemName,
        s.remainingQuantity,
        i.reorderLevel,
        s.batchNumber,
        s.expiryDate,
        i.unit
    )
    FROM StockEntity s
    JOIN s.inventory i
    WHERE s.remainingQuantity > 0
    ORDER BY i.itemSku, s.expiryDate
    """)
    List<InventoryReportDto> getInventoryReports();


    @Query("""
    SELECT new com.example.CakeShopManagement.dto.InventorySummaryReportDto(
    i.itemSku,
    i.itemName,
    i.currentQuantity,
    i.reorderLevel,
    i.unit
    )
    from InventoryEntity i
    ORDER BY i.itemSku
    """)
    List<InventorySummaryReportDto> getInventorySummaryReport();

}
