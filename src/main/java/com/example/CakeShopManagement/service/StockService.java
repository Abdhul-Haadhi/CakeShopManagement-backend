package com.example.CakeShopManagement.service;

import com.example.CakeShopManagement.dto.StockDto;

import java.util.List;

public interface StockService {

    StockDto addStock(StockDto stockDto);
    List<StockDto> getAllStocks();
    List<StockDto> getStockHistory(Long inventoryId);
    String getNextBatchNumber(Long inventoryId);

}
