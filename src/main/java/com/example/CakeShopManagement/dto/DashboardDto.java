package com.example.CakeShopManagement.dto;


import java.util.List;

public class DashboardDto {
    private Long todayOrders;
    private Long pendingOrders;
    private Long todayRevenue;
    private Long availableProducts;
    private Long lowStockCount;
    private Long completedOrders;
    private Long expiringItemsCount;
    private Long expiredItemsCount;
//    private Long totalEmployees;
    private List<OrderStatusDto> orderStatus;
    private List<RevenueChartDto> revenueChart;
    private List<RecentOrderDto> recentOrders;
    private List<LowStockDto> lowStockItems;

    public DashboardDto() {
    }

    public DashboardDto(Long todayOrders, Long pendingOrders, Long todayRevenue, Long availableProducts, Long lowStockCount, Long completedOrders, Long expiringItemsCount, Long expiredItemsCount, List<OrderStatusDto> orderStatus, List<RevenueChartDto> revenueChart, List<RecentOrderDto> recentOrders, List<LowStockDto> lowStockItems) {
        this.todayOrders = todayOrders;
        this.pendingOrders = pendingOrders;
        this.todayRevenue = todayRevenue;
        this.availableProducts = availableProducts;
        this.lowStockCount = lowStockCount;
        this.completedOrders = completedOrders;
        this.expiringItemsCount = expiringItemsCount;
        this.expiredItemsCount = expiredItemsCount;
        this.orderStatus = orderStatus;
        this.revenueChart = revenueChart;
        this.recentOrders = recentOrders;
        this.lowStockItems = lowStockItems;
    }

    public Long getTodayOrders() {
        return todayOrders;
    }

    public void setTodayOrders(Long todayOrders) {
        this.todayOrders = todayOrders;
    }

    public Long getPendingOrders() {
        return pendingOrders;
    }

    public void setPendingOrders(Long pendingOrders) {
        this.pendingOrders = pendingOrders;
    }

    public Long getTodayRevenue() {
        return todayRevenue;
    }

    public void setTodayRevenue(Long todayRevenue) {
        this.todayRevenue = todayRevenue;
    }

    public Long getAvailableProducts() {
        return availableProducts;
    }

    public void setAvailableProducts(Long availableProducts) {
        this.availableProducts = availableProducts;
    }

    public Long getLowStockCount() {
        return lowStockCount;
    }

    public void setLowStockCount(Long lowStockCount) {
        this.lowStockCount = lowStockCount;
    }

    public Long getCompletedOrders() {
        return completedOrders;
    }

    public void setCompletedOrders(Long completedOrders) {
        this.completedOrders = completedOrders;
    }

    public Long getExpiringItemsCount() {
        return expiringItemsCount;
    }

    public void setExpiringItemsCount(Long expiringItemsCount) {
        this.expiringItemsCount = expiringItemsCount;
    }

    public Long getExpiredItemsCount() {
        return expiredItemsCount;
    }

    public void setExpiredItemsCount(Long expiredItemsCount) {
        this.expiredItemsCount = expiredItemsCount;
    }

    public List<OrderStatusDto> getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(List<OrderStatusDto> orderStatus) {
        this.orderStatus = orderStatus;
    }

    public List<RevenueChartDto> getRevenueChart() {
        return revenueChart;
    }

    public void setRevenueChart(List<RevenueChartDto> revenueChart) {
        this.revenueChart = revenueChart;
    }

    public List<RecentOrderDto> getRecentOrders() {
        return recentOrders;
    }

    public void setRecentOrders(List<RecentOrderDto> recentOrders) {
        this.recentOrders = recentOrders;
    }

    public List<LowStockDto> getLowStockItems() {
        return lowStockItems;
    }

    public void setLowStockItems(List<LowStockDto> lowStockItems) {
        this.lowStockItems = lowStockItems;
    }
}
