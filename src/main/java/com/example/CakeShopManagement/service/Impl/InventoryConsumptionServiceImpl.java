package com.example.CakeShopManagement.service.Impl;

import com.example.CakeShopManagement.entity.InventoryEntity;
import com.example.CakeShopManagement.entity.NotificationEntity;
import com.example.CakeShopManagement.entity.StockEntity;
import com.example.CakeShopManagement.exceptions.InsufficientStockException;
import com.example.CakeShopManagement.repository.InventoryRepository;
import com.example.CakeShopManagement.repository.NotificationRepository;
import com.example.CakeShopManagement.repository.StockRepository;
import com.example.CakeShopManagement.service.InventoryConsumptionService;
import jakarta.transaction.Transactional;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class InventoryConsumptionServiceImpl implements InventoryConsumptionService {

    private final StockRepository stockRepository;
    private final InventoryRepository inventoryRepository;

    private final NotificationRepository notificationRepository;
    private final SimpMessagingTemplate messagingTemplate;


    public InventoryConsumptionServiceImpl(StockRepository stockRepository, InventoryRepository inventoryRepository, NotificationRepository notificationRepository, SimpMessagingTemplate messagingTemplate) {
        this.stockRepository = stockRepository;
        this.inventoryRepository = inventoryRepository;
        this.notificationRepository = notificationRepository;
        this.messagingTemplate = messagingTemplate;
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

        // Deduct stock
        inventory.setCurrentQuantity(inventory.getCurrentQuantity()-requiredQty);
        inventoryRepository.save(inventory);

        double lowStockThreshold = inventory.getReorderLevel();

        if(inventory.getCurrentQuantity() <= lowStockThreshold){
            NotificationEntity alert = new NotificationEntity();
            alert.setTitle("Low Stock Alert!");
            alert.setMessage(inventory.getItemName() + " is running critically low. Remaining quantity: " + inventory.getCurrentQuantity());
            alert.setRecipientRole("ADMIN");
            alert.setRead(false);

            notificationRepository.save(alert);

            System.out.println("DEBUG: Low stock condition met for " + inventory.getItemName());
            System.out.println("DEBUG: Sending WebSocket message to /topic/admin/notifications");

            messagingTemplate.convertAndSend("/topic/admin/notifications", alert);
        }
    }

    @Override
    public void validateStock(Long inventoryId, Double requiredQuantity){
        List<StockEntity> stocks = stockRepository.findByInventoryInventoryId(inventoryId);

        if(stocks.isEmpty()){
            throw new RuntimeException("No Stock available.");
        }

        double totalAvailable = stocks.stream().mapToDouble(StockEntity::getRemainingQuantity).sum();

        if(totalAvailable < requiredQuantity){
//            throw new RuntimeException("Insufficient stock available for"+inventoryId+". Available : " + totalAvailable + "\nRequired : " + requiredQuantity);
            throw new RuntimeException("Insufficient stock available for " + stocks.get(0).getInventory().getItemName());
        }
    }
}
