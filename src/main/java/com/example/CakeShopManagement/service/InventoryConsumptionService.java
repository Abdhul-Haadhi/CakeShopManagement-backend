package com.example.CakeShopManagement.service;


public interface InventoryConsumptionService {

    void consumeItem(Long inventoryId, double requiredQty);
}
