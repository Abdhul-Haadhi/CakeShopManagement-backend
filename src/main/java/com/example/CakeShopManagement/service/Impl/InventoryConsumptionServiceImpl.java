package com.example.CakeShopManagement.service.Impl;

import com.example.CakeShopManagement.entity.InventoryEntity;
import com.example.CakeShopManagement.entity.StockEntity;
import com.example.CakeShopManagement.exceptions.InsufficientStockException;
import com.example.CakeShopManagement.repository.InventoryRepository;
import com.example.CakeShopManagement.repository.StockRepository;
import com.example.CakeShopManagement.service.InventoryConsumptionService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class InventoryConsumptionServiceImpl implements InventoryConsumptionService {

    private final StockRepository stockRepository;
    private final InventoryRepository inventoryRepository;


    public InventoryConsumptionServiceImpl(StockRepository stockRepository, InventoryRepository inventoryRepository) {
        this.stockRepository = stockRepository;
        this.inventoryRepository = inventoryRepository;
    }

    @Transactional
    @Override
    public void consumeItem(Long inventoryId, double requiredQty) {

        InventoryEntity inventory = inventoryRepository.findById(inventoryId).orElseThrow(()-> new RuntimeException("Inventory not found"));

        if(inventory.getCurrentQuantity() < requiredQty) {
            throw new RuntimeException(
                    inventory.getItemName() + " does not have enough stock.\n" + "Available : " + inventory.getCurrentQuantity() + "\nRequired : " + requiredQty
            );
        }

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
            throw new InsufficientStockException(inventory.getItemName()+"stock not enough");
        }
        inventory.setCurrentQuantity(inventory.getCurrentQuantity()-requiredQty);
        inventoryRepository.save(inventory);
    }
}
