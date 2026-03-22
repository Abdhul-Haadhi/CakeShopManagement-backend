package com.example.CakeShopManagement.repository;


import com.example.CakeShopManagement.entity.UserEntity;
import com.example.CakeShopManagement.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findFirstByEmail(String email);

    UserEntity findByRole(UserRole userRole);
}
