package com.example.CakeShopManagement.service.Impl;

import com.example.CakeShopManagement.dto.InventoryDto;
import com.example.CakeShopManagement.dto.InventoryReportDto;
import com.example.CakeShopManagement.entity.InventoryEntity;
import com.example.CakeShopManagement.entity.StockEntity;
import com.example.CakeShopManagement.mappers.InventoryMapper;
import com.example.CakeShopManagement.repository.InventoryRepository;
import com.example.CakeShopManagement.repository.StockRepository;
import com.example.CakeShopManagement.service.InventoryService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;
    private final InventoryMapper inventoryMapper;
    private final StockRepository stockRepository;

    public InventoryServiceImpl(InventoryRepository inventoryRepository, InventoryMapper inventoryMapper, StockRepository stockRepository) {
        this.inventoryRepository = inventoryRepository;
        this.inventoryMapper = inventoryMapper;
        this.stockRepository = stockRepository;
    }

    @Override
    public InventoryDto addItem(InventoryDto dto){
        inventoryRepository.findByItemName(dto.getItemName()).ifPresent(item->{
            throw new RuntimeException("Item already exists");
        });

//        String generatedSku = generateNextSku();
//        while (inventoryRepository.existsByItemSku(generatedSku)){
//            generatedSku = generateNextSku();
//        }

        InventoryEntity entity = inventoryMapper.toEntity(dto);
        entity.setItemSku(generateNextSku());
        entity.setCurrentQuantity(0.0);
        InventoryEntity saved = inventoryRepository.save(entity);

        return inventoryMapper.toDto(saved);
    }

    @Override
    public List<InventoryDto> getAllItems(){

        List<InventoryEntity> items = inventoryRepository.findAll();

        return items.stream().map(entity -> {
            InventoryDto dto = inventoryMapper.toDto(entity);
            LocalDate nearest = null;

            if(entity.getStocks() != null){
                for(StockEntity stock : entity.getStocks()){
                    if(stock.getExpiryDate()==null){
                        continue;
                    }
                    if(nearest == null || stock.getExpiryDate().isBefore(nearest)){
                        nearest = stock.getExpiryDate();
                    }
                }
            }
            if(nearest == null){
                dto.setExpiryStatus("No Expiry");
            }
            else {
                long days = ChronoUnit.DAYS.between(LocalDate.now(), nearest);

                if(days<0){
                    dto.setExpiryStatus("Expired");
                }
                else if(days <= 7){
                    dto.setExpiryStatus("Expiring Soon");
                }
                else {
                    dto.setExpiryStatus("Good");
                }
            }
            return dto;
        }).toList();
    }


    @Override
    public InventoryDto getItem(Long id){

        InventoryEntity entity =inventoryRepository.findById(id).orElseThrow(() ->new RuntimeException("Item not found"));

        InventoryDto dto = inventoryMapper.toDto(entity);
        LocalDate nearest = null;

        if(entity.getStocks() != null){
            for(StockEntity stock : entity.getStocks()){
                if(stock.getExpiryDate()==null){
                    continue;
                }
                if(nearest == null || stock.getExpiryDate().isBefore(nearest)){
                    nearest = stock.getExpiryDate();
                }
            }
        }
        if(nearest == null){
            dto.setExpiryStatus("No Expiry");
        }
        else {
            long days = ChronoUnit.DAYS.between(LocalDate.now(), nearest);

            if(days<0){
                dto.setExpiryStatus("Expired");
            }
            else if(days <= 7){
                dto.setExpiryStatus("Expiring Soon");
            }
            else {
                dto.setExpiryStatus("Good");
            }
        }
        return dto;
    }

    @Override
    public List<InventoryReportDto> getInventoryReport(){
//        List<InventoryEntity> inventories = inventoryRepository.findAll();
//
//        return inventories.stream().map(item->{
//            List<StockEntity> stocks = stockRepository.findByInventoryInventoryIdOrderByExpiryDateAsc(item.getInventoryId());
//
//            String batchNo = "";
//            LocalDate expiry = null;
//
//            if(!stocks.isEmpty()){
//                StockEntity firstStock = stocks.stream()
//                        .filter(s -> s.getRemainingQuantity() > 0)
//                        .findFirst()
//                        .orElse(null);
//
//                if(firstStock != null){
//                    batchNo = firstStock.getBatchNumber();
//                    expiry = firstStock.getExpiryDate();
//                }
//            }
//            return new InventoryReportDto(
//                    item.getItemSku(),
//                    item.getItemName(),
//                    item.getCurrentQuantity(),
//                    item.getReorderLevel(),
//                    batchNo,
//                    expiry,
//                    item.getUnit()
//
//            );
//        }).toList();
        return inventoryRepository.getInventoryReports();
    }

    private String generateNextSku(){
        Long count = inventoryRepository.count() + 1;

        return "ITM" + String.format("%04d", count);
    }

    @Override
    public String getItemBySku(){
        return generateNextSku();
    }


    @Override
    public InventoryDto updateItem(Long id, InventoryDto dto){
        InventoryEntity entity = inventoryRepository.findById(id).orElseThrow(()->new RuntimeException("Item not found"));

        entity.setItemName(dto.getItemName());
        entity.setCategory(dto.getCategory());
        entity.setUnit(dto.getUnit());
        entity.setReorderLevel(dto.getReorderLevel());

        return inventoryMapper.toDto(inventoryRepository.save(entity));
    }

    @Override
    public void deleteItem(Long id){
        if(stockRepository.existsByInventoryInventoryId(id)){
            throw new RuntimeException("Cannot delete. Stock records already exist.");
        }
        InventoryEntity entity = inventoryRepository.findById(id).orElseThrow(()->new RuntimeException("Item not found"));

        inventoryRepository.delete(entity);
    }

}
