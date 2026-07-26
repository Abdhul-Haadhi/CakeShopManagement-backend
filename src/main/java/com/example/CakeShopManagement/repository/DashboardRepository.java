package com.example.CakeShopManagement.repository;

import com.example.CakeShopManagement.dto.LowStockDto;
import com.example.CakeShopManagement.dto.RecentOrderDto;
import com.example.CakeShopManagement.dto.RevenueChartDto;
import com.example.CakeShopManagement.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Repository
public interface DashboardRepository extends JpaRepository<OrderEntity,Long> {
    // Today's Orders
    Long countByOrderDateBetween(Date start, Date end);

    // Today's Revenue
    @Query("""
    SELECT coalesce(sum(o.totalAmount),0)
    FROM OrderEntity o
    where o.orderDate BETWEEN :start AND :end
    """)
    Long getTodayRevenue(Date start, Date end);

    // Pending Orders
    Long countByStatus(String status);

    // Available Products
    @Query("""
    SELECT COUNT(p)
    FROM ProductEntity p
    WHERE p.active = true
    """)
    Long getAvailableProducts();

    // Low Stock Count
    @Query("""
    SELECT count(i)
    FROM InventoryEntity i
    WHERE i.currentQuantity <= i.reorderLevel
    """)
    Long getLowStockCount();

    // Order Status
    @Query("""
        SELECT o.status, COUNT(o)
        FROM OrderEntity o
        GROUP BY o.status
    """)
    List<Object[]> getOrderStatus();

    // Revenue Chart
    @Query("""
    SELECT new com.example.CakeShopManagement.dto.RevenueChartDto(
        cast(o.orderDate as date),
        sum(o.totalAmount)
    )
    FROM OrderEntity o
    WHERE o.orderDate >= :startDate
    GROUP BY cast(o.orderDate as date)
    ORDER BY cast(o.orderDate as date)
    """)
    List<RevenueChartDto> getRevenueChart(Date startDate);

    // Recent Orders
    @Query("""
    SELECT new com.example.CakeShopManagement.dto.RecentOrderDto(
        o.orderId,
        coalesce(c.customerName,o.customerName),
        o.totalAmount,
        o.status,
        o.orderDate
    )
    FROM OrderEntity o
    LEFT JOIN o.customer c
    ORDER BY o.orderDate DESC 
    """)
    List<RecentOrderDto> getRecentOrders();


    // Low Stock Items
    @Query("""
    SELECT new com.example.CakeShopManagement.dto.LowStockDto(
        i.inventoryId,
        i.itemName,
        i.currentQuantity,
        i.reorderLevel
    )
    from InventoryEntity i
    WHERE i.currentQuantity <= i.reorderLevel
    ORDER BY i.currentQuantity ASC 
    """)
    List<LowStockDto> getLowStockItems();


}
