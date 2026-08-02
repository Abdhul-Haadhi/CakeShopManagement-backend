package com.example.CakeShopManagement.repository;

import com.example.CakeShopManagement.entity.StockEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
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

    List<StockEntity> findByInventoryInventoryIdOrderByExpiryDateAsc(Long inventoryId);

    @Query("""
    SELECT count (s)
    from StockEntity s
    where s.remainingQuantity > 0 and s.expiryDate BETWEEN :today and :expiryDate
    """)
    Long getExpiringItemsCount(LocalDate today, LocalDate expiryDate);


    @Query("""
select count (s)
from StockEntity s
where s.remainingQuantity > 0 and s.expiryDate between :today and :expiryDate
""")
    Long getExpiredItemsCount(LocalDate today, LocalDate expiryDate);
}
