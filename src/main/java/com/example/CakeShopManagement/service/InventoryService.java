package com.example.CakeShopManagement.service;

import com.example.CakeShopManagement.dto.InventoryDto;
import com.example.CakeShopManagement.dto.InventoryReportDto;
import com.example.CakeShopManagement.dto.InventorySummaryReportDto;

import java.util.List;

public interface InventoryService {

    InventoryDto addItem(InventoryDto dto);

    InventoryDto updateItem(Long id, InventoryDto dto);

    void deleteItem(Long id);

    InventoryDto getItem(Long id);

    List<InventoryDto> getAllItems();

    public List<InventoryReportDto> getInventoryBatchReport();
    List<InventorySummaryReportDto> getInventorySummaryReport();

//    boolean getItemBySku(String itemSku);
    String getItemBySku();
}
