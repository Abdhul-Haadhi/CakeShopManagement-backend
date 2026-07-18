package com.example.CakeShopManagement.controller;

import com.example.CakeShopManagement.dto.NotificationDto;
import com.example.CakeShopManagement.entity.NotificationEntity;
import com.example.CakeShopManagement.repository.NotificationRepository;
import com.example.CakeShopManagement.service.NotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class NotificationController {

    private final NotificationService notificationService;
    private final NotificationRepository notificationRepository;

    public NotificationController(NotificationService notificationService, NotificationRepository notificationRepository) {
        this.notificationService = notificationService;
        this.notificationRepository = notificationRepository;
    }

    @GetMapping("/admin/notifications")
    public ResponseEntity<List<NotificationDto>> getAdminNotifications() {
        return ResponseEntity.ok(notificationService.getAdminNotifications());
    }

    @GetMapping("/customer/{customerId}/notifications")
    public ResponseEntity<List<NotificationDto>> getCustomerNotifications(@PathVariable Long customerId) {
        return ResponseEntity.ok(notificationService.getCustomerNotifications(customerId));
    }

    @GetMapping("/admin/notifications/unread")
    public ResponseEntity<List<NotificationEntity>> getUnreadNotifications() {
        List<NotificationEntity> unread = notificationRepository.findByRecipientRoleAndIsReadOrderByCreatedAtDesc("ADMIN", false);
        return ResponseEntity.ok(unread);
    }

    @PostMapping("/admin/notifications/mark-read")
    public ResponseEntity<Void> markAsRead(){
        List<NotificationEntity> unread = notificationRepository.findByRecipientRoleAndIsReadOrderByCreatedAtDesc("ADMIN", false);

        for(NotificationEntity notification : unread){
            notification.setRead(true);
        }

        notificationRepository.saveAll(unread);
        return ResponseEntity.ok().build();
    }

}
