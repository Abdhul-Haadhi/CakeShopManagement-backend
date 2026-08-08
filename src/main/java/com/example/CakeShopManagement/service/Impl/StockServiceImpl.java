package com.example.CakeShopManagement.service.Impl;

import com.example.CakeShopManagement.dto.InventoryDto;
import com.example.CakeShopManagement.dto.StockDto;
import com.example.CakeShopManagement.dto.StockTransactionDto;
import com.example.CakeShopManagement.dto.StockTransactionReportDto;
import com.example.CakeShopManagement.entity.InventoryEntity;
import com.example.CakeShopManagement.entity.NotificationEntity;
import com.example.CakeShopManagement.entity.StockEntity;
import com.example.CakeShopManagement.entity.StockTransactionEntity;
import com.example.CakeShopManagement.enums.TransactionType;
import com.example.CakeShopManagement.repository.InventoryRepository;
import com.example.CakeShopManagement.repository.NotificationRepository;
import com.example.CakeShopManagement.repository.StockRepository;
import com.example.CakeShopManagement.repository.StockTransactionRepository;
import com.example.CakeShopManagement.service.StockService;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class StockServiceImpl implements StockService {

    private final StockRepository stockRepository;
    private final InventoryRepository inventoryRepository;
    private final StockTransactionRepository stockTransactionRepository;
    private final NotificationRepository notificationRepository;
    private final SimpMessagingTemplate messagingTemplate;

    public StockServiceImpl(StockRepository stockRepository, InventoryRepository inventoryRepository, StockTransactionRepository stockTransactionRepository, NotificationRepository notificationRepository, SimpMessagingTemplate messagingTemplate) {
        this.stockRepository = stockRepository;
        this.inventoryRepository = inventoryRepository;
        this.stockTransactionRepository = stockTransactionRepository;
        this.notificationRepository = notificationRepository;
        this.messagingTemplate = messagingTemplate;
    }

    @Override
    public StockDto addStock(StockDto dto){
        InventoryEntity inventory = inventoryRepository.findById(dto.getInventoryId()).orElseThrow(()->new RuntimeException("Item not found"));

        StockEntity stock = new StockEntity();

        stock.setInventory(inventory);
        stock.setQuantityAdded(dto.getQuantityAdded());
        stock.setExpiryDate(dto.getExpiryDate());
        stock.setReceivedDate(LocalDate.now());
        stock.setBatchNumber(generateBatchNumber(inventory));
        stock.setRemainingQuantity(dto.getQuantityAdded());

        stockRepository.save(stock);

        StockTransactionEntity transaction = new StockTransactionEntity();

        transaction.setStock(stock);
        transaction.setTransactionType(TransactionType.IN);
        transaction.setQuantity(dto.getQuantityAdded());
        transaction.setReason("Stock Added");
        transaction.setTransactionDate(LocalDate.now());

        stockTransactionRepository.save(transaction);

        inventory.setCurrentQuantity(inventory.getCurrentQuantity() + dto.getQuantityAdded());

        inventoryRepository.save(inventory);

        return dto;

    }

//    private String generateBatchNumber(){
//        String date = LocalDate.now().toString().replace("-", "");
//
//        long count = stockRepository.count() + 1;
//
//        return "BATCH-" + date + "-" + String.format("%04d", count);
//    }

    private String generateBatchNumber(InventoryEntity inventory) {
        String date = LocalDate.now().toString().replace("-", "");

        long nextBatch = stockRepository.countByInventoryInventoryId(inventory.getInventoryId())+1;

        String prefix = inventory.getItemName().replaceAll("[^A-Za-z0-9]", "").toUpperCase();

        if(prefix.length() >= 3){
            prefix = prefix.substring(0,3);
        }

        return prefix + "-" + date + "-" + String.format("%04d", nextBatch);
    }

    @Override
    public String getNextBatchNumber(Long inventoryId){
        InventoryEntity inventory = inventoryRepository.findById(inventoryId).orElseThrow(()->new RuntimeException("Item not found"));

        return generateBatchNumber(inventory);
    }


    private StockDto convertToDto(StockEntity stock){

        StockDto dto = new StockDto();

        dto.setStockId(stock.getStockId());
        dto.setInventoryId(stock.getInventory().getInventoryId());
        dto.setItemName(stock.getInventory().getItemName());

        dto.setQuantityAdded(stock.getQuantityAdded());
        dto.setRemainingQuantity(stock.getRemainingQuantity());
//        dto.setQuantityDeducted(stock.getQuantityDeducted());
//        dto.setDeductionReason(stock.getDeductionReason());
        dto.setExpiryDate(stock.getExpiryDate());
        dto.setReceivedDate(stock.getReceivedDate());
        dto.setBatchNumber(stock.getBatchNumber());

        return dto;

    }

//    @Override
//    public StockDto deductStock(Long stockId, Double quantity, String reason){
//        StockEntity stock = stockRepository.findById(stockId).orElseThrow(()->new RuntimeException("Stock batch not found"));
//
//        if(quantity <= 0){
//            throw new RuntimeException("Invalid deduction quantity");
//        }
//        if(stock.getRemainingQuantity() < quantity){
//            throw new RuntimeException("Insufficient stock. Remaining quantity: " + stock.getRemainingQuantity());
//        }
//        if(stock.getExpiryDate().isBefore(LocalDate.now())){
//            throw new RuntimeException("Item expired on " + stock.getExpiryDate());
//        }
//
//        stock.setRemainingQuantity(stock.getRemainingQuantity() - quantity);
////        Double deducted = stock.getQuantityDeducted() == null ? 0.0 : stock.getQuantityDeducted();
////        stock.setQuantityDeducted(deducted + quantity);
////        stock.setDeductionReason(reason);
//
//        stockRepository.save(stock);
//
//        StockTransactionEntity transaction = new StockTransactionEntity();
//        transaction.setStock(stock);
//        transaction.setTransactionType(TransactionType.OUT);
//        transaction.setQuantity(quantity);
//        transaction.setReason(reason);
//        transaction.setTransactionDate(LocalDate.now());
//        stockTransactionRepository.save(transaction);
//
//        InventoryEntity inventory = stock.getInventory();
//        inventory.setCurrentQuantity(inventory.getCurrentQuantity() - quantity);
//
//        inventoryRepository.save(inventory);
//        return convertToDto(stock);
//    }


    @Override
    @Transactional
    public StockDto deductStock(Long stockId, Double quantity, String reason) {
        // 1. Fetch the specific stock batch by stockId
        StockEntity stock = stockRepository.findById(stockId)
                .orElseThrow(() -> new RuntimeException("Stock batch not found with ID: " + stockId));

        // 2. Fetch associated inventory item
        InventoryEntity inventory = stock.getInventory();

        // 3. Check batch availability
        if (stock.getRemainingQuantity() < quantity) {
            throw new RuntimeException("Insufficient batch stock. Available: "
                    + stock.getRemainingQuantity() + ", Requested: " + quantity);
        }

        // 4. Update batch & overall inventory quantities
        stock.setRemainingQuantity(stock.getRemainingQuantity() - quantity);
        inventory.setCurrentQuantity(inventory.getCurrentQuantity() - quantity);

        StockEntity updatedStock = stockRepository.save(stock);
        inventoryRepository.save(inventory);

        // -------------------------------------------------------------
        // 5. TRIGGER NOTIFICATION: Stock Deducted Event
        // -------------------------------------------------------------
        NotificationEntity deductionAlert = new NotificationEntity();
        deductionAlert.setTitle("Stock Deducted");
        deductionAlert.setMessage(quantity + " units of '" + inventory.getItemName()
                + "' were deducted. Remaining batch qty: " + updatedStock.getRemainingQuantity()
                + (reason != null && !reason.isBlank() ? " (Reason: " + reason + ")" : ""));
        deductionAlert.setModule("INVENTORY");
        deductionAlert.setRecipientRole("ADMIN");
        deductionAlert.setRead(false);
        deductionAlert.setCreatedAt(LocalDate.now());

        // Save for persistent historical unread lists
        notificationRepository.save(deductionAlert);

        // Broadcast real-time message to connected clients
        messagingTemplate.convertAndSend("/topic/admin/notifications", deductionAlert);

        // -------------------------------------------------------------
        // 6. CHECK REORDER LEVEL: Trigger Low Stock Alert if needed
        // -------------------------------------------------------------
        if (inventory.getCurrentQuantity() <= inventory.getReorderLevel()) {
            NotificationEntity lowStockAlert = new NotificationEntity();
            lowStockAlert.setTitle("Low Stock Alert!");
            lowStockAlert.setMessage(inventory.getItemName() + " is running critically low. Total remaining: " + inventory.getCurrentQuantity());
            lowStockAlert.setModule("INVENTORY");
            lowStockAlert.setRecipientRole("ADMIN");
            lowStockAlert.setRead(false);
            lowStockAlert.setCreatedAt(LocalDate.now());

            notificationRepository.save(lowStockAlert);
            messagingTemplate.convertAndSend("/topic/admin/notifications", lowStockAlert);
        }

        // 7. Map updated StockEntity to StockDto and return
        return mapToDto(updatedStock);
    }

    private StockDto mapToDto(StockEntity stock) {
        StockDto dto = new StockDto();
        dto.setStockId(stock.getStockId());
        dto.setBatchNumber(stock.getBatchNumber());
//        dto.setQuantity(stock.getQuantity());
        dto.setQuantityAdded(stock.getQuantityAdded());
        dto.setRemainingQuantity(stock.getRemainingQuantity());
        dto.setExpiryDate(stock.getExpiryDate());
        dto.setInventoryId(stock.getInventory().getInventoryId());
        return dto;
    }

    @Override
    public List<StockDto> getAllStocks(){
        return stockRepository.findAllByOrderByReceivedDateDesc().stream().map(this::convertToDto).toList();
    }

    @Override
    public List<StockDto> getStockHistory(Long inventoryId){

        return stockRepository.findByInventoryInventoryIdOrderByReceivedDateDesc(inventoryId)
                .stream()
                .map(this::convertToDto)
                .toList();

    }

    @Override
    public List<StockTransactionDto> getStockTransactions(Long inventoryId){

        return stockTransactionRepository
                .findByStockInventoryInventoryIdOrderByTransactionDateDesc(inventoryId)
                .stream()
                .map(this::convertTransactionToDto)
                .toList();
    }

    @Override
    public List<StockTransactionReportDto> getStockTransactionReport(){
        return stockTransactionRepository.getTransactionReport();
    }

    @Override
    public StockTransactionDto convertTransactionToDto(StockTransactionEntity transaction){

        StockTransactionDto dto = new StockTransactionDto();

        dto.setTransactionId(transaction.getTransactionId());
        dto.setTransactionType(transaction.getTransactionType());
        dto.setQuantity(transaction.getQuantity());
        StockEntity stock = new StockEntity();
        dto.setBatchNumber(transaction.getStock().getBatchNumber());
        dto.setReason(transaction.getReason());
        dto.setTransactionDate(transaction.getTransactionDate());

        return dto;
    }

}
