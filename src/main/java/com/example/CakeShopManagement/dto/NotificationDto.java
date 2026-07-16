package com.example.CakeShopManagement.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class NotificationDto {

    private Long notificationId;
    private String title;
    private String message;
    private String module;
    private String recipientRole;
    private Long recipientUserId;
    private boolean isRead;
    private LocalDate createdAt;

    public NotificationDto() {
    }

    public NotificationDto(Long notificationId, String title, String message, String module, String recipientRole, Long recipientUserId, boolean isRead, LocalDate createdAt) {
        this.notificationId = notificationId;
        this.title = title;
        this.message = message;
        this.module = module;
        this.recipientRole = recipientRole;
        this.recipientUserId = recipientUserId;
        this.isRead = isRead;
        this.createdAt = createdAt;
    }

    public Long getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(Long notificationId) {
        this.notificationId = notificationId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getRecipientRole() {
        return recipientRole;
    }

    public void setRecipientRole(String recipientRole) {
        this.recipientRole = recipientRole;
    }

    public Long getRecipientUserId() {
        return recipientUserId;
    }

    public void setRecipientUserId(Long recipientUserId) {
        this.recipientUserId = recipientUserId;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }
}
