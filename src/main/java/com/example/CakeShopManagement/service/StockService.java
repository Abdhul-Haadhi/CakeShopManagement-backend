package com.example.CakeShopManagement.service;

import com.example.CakeShopManagement.dto.StockDto;
import com.example.CakeShopManagement.dto.StockTransactionDto;
import com.example.CakeShopManagement.dto.StockTransactionReportDto;
import com.example.CakeShopManagement.entity.StockTransactionEntity;

import java.util.List;

public interface StockService {

    StockDto addStock(StockDto stockDto);
    List<StockDto> getAllStocks();
    List<StockDto> getStockHistory(Long inventoryId);
    String getNextBatchNumber(Long inventoryId);
    StockDto deductStock(Long stockId, Double quantity, String reason);
    List<StockTransactionDto> getStockTransactions(Long inventoryId);
    StockTransactionDto convertTransactionToDto(StockTransactionEntity transaction);
    List<StockTransactionReportDto> getStockTransactionReport();

}
