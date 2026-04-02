package com.example.CakeShopManagement.repository;

import com.example.CakeShopManagement.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRegistrationRepository extends JpaRepository<ProductEntity, Long> {

    List<ProductEntity> findAllByProductNameContaining(String title);
}
