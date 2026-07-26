package com.example.CakeShopManagement.service.Impl;

import com.example.CakeShopManagement.dto.DashboardDto;
import com.example.CakeShopManagement.dto.OrderStatusDto;
import com.example.CakeShopManagement.repository.DashboardRepository;
import com.example.CakeShopManagement.service.DashboardService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class DashboardServiceImpl implements DashboardService {

    private final DashboardRepository repository;

    public DashboardServiceImpl(DashboardRepository repository) {
        this.repository = repository;
    }

    @Override
    public DashboardDto getDashboard() {
        DashboardDto dto = new DashboardDto();

        LocalDate today = LocalDate.now();

        Date start = Date.from(today.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date end = Date.from(today.plusDays(1).atStartOfDay(ZoneId.systemDefault()).minusSeconds(1).toInstant());

        dto.setTodayOrders(repository.countByOrderDateBetween(start,end));
        dto.setTodayRevenue(repository.getTodayRevenue(start,end));
        dto.setPendingOrders(repository.countByStatus("Pending"));
        dto.setCompletedOrders(repository.countByStatus("Delivered"));
        dto.setAvailableProducts(repository.getAvailableProducts());
        dto.setLowStockCount(repository.getLowStockCount());
//        dto.setRevenueChart(repository.getRevenueChart(today.minusDays(30)));
        Date startDate = Date.from(today.minusDays(30)
                        .atStartOfDay(ZoneId.systemDefault())
                        .toInstant()
        );

        dto.setRevenueChart(repository.getRevenueChart(startDate));
        dto.setRecentOrders(repository.getRecentOrders().stream().limit(5).toList());
        dto.setLowStockItems(repository.getLowStockItems().stream().limit(5).toList());

        List<OrderStatusDto> statusList = new ArrayList<>();

        for(Object[] row : repository.getOrderStatus()){
            statusList.add(
                    new OrderStatusDto(
                            (String) row[0],
                            (long) ((Long) row[1]).intValue()
                    )
            );
        }
        dto.setOrderStatus(statusList);
        return dto;
    }
}
