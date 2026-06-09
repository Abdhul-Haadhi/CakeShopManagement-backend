package com.example.CakeShopManagement.repository;

import com.example.CakeShopManagement.entity.ProductCustomizationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ProductCustomizationRepository extends JpaRepository<ProductCustomizationEntity,Long> {
    List<ProductCustomizationEntity> findByProductProductId(Long productId);

    @Transactional
    @Modifying
    void deleteByProductProductId(Long productId);
}
