package com.example.CakeShopManagement.repository;

import com.example.CakeShopManagement.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {
    Optional<CustomerEntity> findByPhone(String phone);
    Optional<CustomerEntity> findByEmail(String email);
    boolean existsByEmailAndCustomerIdNot(String email, Long customerId);
    boolean existsByPhoneAndCustomerIdNot(String phone, Long customerId);
}
