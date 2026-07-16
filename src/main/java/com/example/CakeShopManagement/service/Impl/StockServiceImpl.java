package com.example.CakeShopManagement.service.Impl;

import com.example.CakeShopManagement.dto.StockDto;
import com.example.CakeShopManagement.entity.InventoryEntity;
import com.example.CakeShopManagement.entity.StockEntity;
import com.example.CakeShopManagement.repository.InventoryRepository;
import com.example.CakeShopManagement.repository.StockRepository;
import com.example.CakeShopManagement.service.StockService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class StockServiceImpl implements StockService {

    private final StockRepository stockRepository;
    private final InventoryRepository inventoryRepository;

    public StockServiceImpl(StockRepository stockRepository, InventoryRepository inventoryRepository) {
        this.stockRepository = stockRepository;
        this.inventoryRepository = inventoryRepository;
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
        dto.setExpiryDate(stock.getExpiryDate());
        dto.setReceivedDate(stock.getReceivedDate());
        dto.setBatchNumber(stock.getBatchNumber());

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

}
