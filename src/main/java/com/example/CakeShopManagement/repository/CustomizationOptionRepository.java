package com.example.CakeShopManagement.repository;

import com.example.CakeShopManagement.entity.CustomizationOptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomizationOptionRepository extends JpaRepository<CustomizationOptionEntity, Long> {

}
