package com.example.CakeShopManagement.repository;

import com.example.CakeShopManagement.dto.MonthlySalesDto;
import com.example.CakeShopManagement.dto.TopSellingProductDto;
import com.example.CakeShopManagement.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

    List<OrderEntity> findBySessionIdOrderByOrderDateDesc(String sessionId);
    List<OrderEntity> findByCustomerCustomerIdOrderByOrderDateDesc(Long customerId);
    List<OrderEntity> findAllByOrderByOrderDateDesc();

    @Query("""
SELECT COUNT(o)
FROM OrderEntity o
WHERE o.status='Delivered'
AND o.orderDate BETWEEN :startDate AND :endDate
""")
    Long getTotalOrders(Date startDate, Date endDate);


    @Query("""
SELECT COALESCE(SUM(o.totalAmount),0)
FROM OrderEntity o
WHERE o.status='Delivered'
and o.orderDate BETWEEN :startDate AND :endDate
""")
    Long getTotalRevenue(Date startDate, Date endDate);

    @Query("""
SELECT COALESCE(SUM(oi.quantity),0)
FROM OrderItemEntity oi
WHERE oi.order.status='Delivered'
AND oi.order.orderDate BETWEEN :startDate AND :endDate
""")
    Long getTotalItemsSold(Date startDate, Date endDate);

    @Query("""
SELECT new com.example.CakeShopManagement.dto.MonthlySalesDto(
FUNCTION('MONTHNAME',o.orderDate),
COUNT(o),
COALESCE(SUM(o.totalAmount),0)
)
FROM OrderEntity o
WHERE o.status='Delivered'
AND o.orderDate BETWEEN :startDate AND :endDate
GROUP BY FUNCTION('MONTH',o.orderDate),FUNCTION('MONTHNAME',o.orderDate)
ORDER BY FUNCTION('MONTH',o.orderDate)
""")
    List<MonthlySalesDto> getMonthlySales(Date startDate, Date endDate);


    @Query("""
SELECT new com.example.CakeShopManagement.dto.TopSellingProductDto(
p.productName,
c.categoryName,
SUM(oi.quantity),
SUM(oi.price*oi.quantity)
)
FROM OrderItemEntity oi
JOIN oi.product p
JOIN p.categoryEntity c
WHERE oi.order.status = 'Delivered'
AND oi.order.orderDate BETWEEN :startDate AND :endDate
GROUP BY p.productId,p.productName,c.categoryName
ORDER BY SUM(oi.quantity) DESC
""")
    List<TopSellingProductDto> getTopProducts(Date startDate, Date endDate);

}

