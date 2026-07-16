package com.example.CakeShopManagement.service;

import com.example.CakeShopManagement.dto.NotificationDto;

import java.util.List;

public interface NotificationService {
    void notifyAdmin(String title, String message, String module);
    void notifyCustomer(Long customerId, String title, String message, String module);
    List<NotificationDto> getAdminNotifications();
    List<NotificationDto> getCustomerNotifications(Long customerId);
}
