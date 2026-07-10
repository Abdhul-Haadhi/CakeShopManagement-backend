package com.example.CakeShopManagement.service.Impl;

import com.example.CakeShopManagement.entity.InventoryEntity;
import com.example.CakeShopManagement.entity.StockEntity;
import com.example.CakeShopManagement.repository.InventoryRepository;
import com.example.CakeShopManagement.repository.StockRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryConsumptionServiceImpl {

    private final StockRepository stockRepository;
    private final InventoryRepository inventoryRepository;


    public InventoryConsumptionServiceImpl(StockRepository stockRepository, InventoryRepository inventoryRepository) {
        this.stockRepository = stockRepository;
        this.inventoryRepository = inventoryRepository;
    }

    public void consumeItem(Long inventoryId,double requiredQty) {
        InventoryEntity inventory = inventoryRepository.findById(inventoryId).orElseThrow();

        List<StockEntity> batches = stockRepository.findByInventoryInventoryIdAndRemainingQuantityGreaterThanOrderByExpiryDateAscReceivedDateAsc(inventoryId,0.0);

        double remainingNeed = requiredQty;
        for(StockEntity batch : batches) {
            if(remainingNeed <= 0){
                break;
            }
            double available = batch.getRemainingQuantity();

            if(available >= remainingNeed){
                batch.setRemainingQuantity(available -remainingNeed);
                remainingNeed = 0;
            }
            else {
                batch.setRemainingQuantity(0.0);
                remainingNeed -= available;
            }
            stockRepository.save(batch);
        }
        if(remainingNeed > 0){
            throw new RuntimeException(inventory.getItemName()+"stock not enough");
        }
        inventory.setCurrentQuantity(inventory.getCurrentQuantity()-requiredQty);
        inventoryRepository.save(inventory);
    }
}
