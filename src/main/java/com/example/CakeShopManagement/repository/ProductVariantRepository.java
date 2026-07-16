package com.example.CakeShopManagement.repository;

import com.example.CakeShopManagement.entity.ProductVariant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductVariantRepository extends JpaRepository<ProductVariant, Long> {
    void deleteByProductProductId(Long productId);
}
