package com.example.CakeShopManagement.repository;

import com.example.CakeShopManagement.entity.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Long> {
    Optional<EmployeeEntity> findByEmail(String email);
    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);
    boolean existsByEmailAndEmployeeIdNot(String email, Long employeeId);
    boolean existsByPhoneAndEmployeeIdNot(String phone, Long employeeId);
}
