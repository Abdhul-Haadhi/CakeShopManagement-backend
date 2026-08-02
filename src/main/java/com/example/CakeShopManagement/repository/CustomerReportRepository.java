package com.example.CakeShopManagement.repository;

import com.example.CakeShopManagement.dto.CustomerReportDto;
import com.example.CakeShopManagement.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerReportRepository extends JpaRepository<OrderEntity,Long> {

    @Query("""
SELECT new com.example.CakeShopManagement.dto.CustomerReportDto(
COALESCE(MAX(c.customerName), MAX(o.customerName)),
o.phone,
count(o.orderId),
SUM(o.totalAmount),
MAX(o.orderDate),
MIN(o.orderDate),
CASE
 WHEN MAX(c.customerId) IS NOT NULL THEN 'Registered'
 ELSE 'Guest'
END
 )
FROM OrderEntity o
LEFT JOIN o.customer c
GROUP BY o.phone
ORDER BY SUM (o.totalAmount) DESC
""")
    List<CustomerReportDto> getCustomerReport();
}
