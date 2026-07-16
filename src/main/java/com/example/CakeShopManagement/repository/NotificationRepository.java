package com.example.CakeShopManagement.repository;

import com.example.CakeShopManagement.entity.NotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<NotificationEntity, Long> {

    List<NotificationEntity> findByRecipientRoleOrderByCreatedAtDesc(String role);
    List<NotificationEntity> findByRecipientUserIdOrderByCreatedAtDesc(Long userId);
}
