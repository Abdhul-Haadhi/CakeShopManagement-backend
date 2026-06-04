package com.example.CakeShopManagement.repository;

import com.example.CakeShopManagement.entity.CustomizationOptionValueEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomizationOptionValueRepository extends JpaRepository<CustomizationOptionValueEntity,Long> {

    List<CustomizationOptionValueEntity> findByOptionOptionId(Long optionId);
}
