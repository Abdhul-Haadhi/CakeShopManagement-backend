package com.example.CakeShopManagement.service.Impl;

import com.example.CakeShopManagement.dto.NotificationDto;
import com.example.CakeShopManagement.entity.NotificationEntity;
import com.example.CakeShopManagement.repository.NotificationRepository;
import com.example.CakeShopManagement.service.NotificationService;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final SimpMessagingTemplate messagingTemplate;

    public NotificationServiceImpl(NotificationRepository notificationRepository, SimpMessagingTemplate messagingTemplate) {
        this.notificationRepository = notificationRepository;
        this.messagingTemplate = messagingTemplate;
    }


    @Override
    public void notifyAdmin(String title, String message, String module){
        NotificationEntity notification = new NotificationEntity();
        notification.setTitle(title);
        notification.setMessage(message);
        notification.setModule(module);
        notification.setRecipientRole("ADMIN");
        notification.setCreatedAt(LocalDate.now());

        NotificationEntity saved = notificationRepository.save(notification);
        NotificationDto dto = mapToDto(saved);

        messagingTemplate.convertAndSend("/topic/admin/notifications",dto);
    }

    @Override
    public void notifyCustomer(Long customerId, String title, String message, String module){
        NotificationEntity notification = new NotificationEntity();
        notification.setTitle(title);
        notification.setMessage(message);
        notification.setModule(module);
        notification.setRecipientRole("CUSTOMER");
        notification.setRecipientUserId(customerId);
        notification.setCreatedAt(LocalDate.now());

        NotificationEntity saved = notificationRepository.save(notification);
        NotificationDto dto = mapToDto(saved);

        messagingTemplate.convertAndSend("/topic/customer/"+ customerId +"/notifications",dto);
    }

    @Override
    public List<NotificationDto> getAdminNotifications(){
        return notificationRepository.findByRecipientRoleOrderByCreatedAtDesc("ADMIN")
                .stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @Override
    public List<NotificationDto> getCustomerNotifications(Long customerId){
        return notificationRepository.findByRecipientUserIdOrderByCreatedAtDesc(customerId)
                .stream().map(this::mapToDto).collect(Collectors.toList());
    }

    private NotificationDto mapToDto(NotificationEntity notificationEntity) {
        NotificationDto dto =new NotificationDto();

        dto.setNotificationId(notificationEntity.getNotificationId());
        dto.setTitle(notificationEntity.getTitle());
        dto.setMessage(notificationEntity.getMessage());
        dto.setModule(notificationEntity.getModule());
        dto.setRecipientRole(notificationEntity.getRecipientRole());
        dto.setRecipientUserId(notificationEntity.getRecipientUserId());
        dto.setRead(notificationEntity.getRead());
        dto.setCreatedAt(notificationEntity.getCreatedAt());

        return dto;
    }

}
