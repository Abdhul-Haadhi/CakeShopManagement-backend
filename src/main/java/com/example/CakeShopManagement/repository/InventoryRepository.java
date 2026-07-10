package com.example.CakeShopManagement.repository;

import com.example.CakeShopManagement.entity.InventoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InventoryRepository extends JpaRepository<InventoryEntity,Long> {

    Optional<InventoryEntity> findByItemName(String itemName);
    Boolean existsByItemSku(String itemSku);

}
