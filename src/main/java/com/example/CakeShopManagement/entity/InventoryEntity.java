package com.example.CakeShopManagement.entity;

import com.example.CakeShopManagement.enums.InventoryCategory;
import com.example.CakeShopManagement.enums.InventoryUnit;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "inventory")
public class InventoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long inventoryId;

    private String itemSku;

    @Column(nullable=false, unique=true)
    private String itemName;

    @Enumerated(EnumType.STRING)
    private InventoryCategory category;

    @Enumerated(EnumType.STRING)
    private InventoryUnit unit;

    private Double reorderLevel;

    @Column(nullable = false)
    private Double currentQuantity = 0.0;

    @OneToMany(mappedBy = "inventory", fetch = FetchType.LAZY)
    private List<StockEntity> stocks;

    @Column(nullable = false)
    private Boolean isScalable = true;

    public InventoryEntity() {
    }

    public InventoryEntity(Long inventoryId, String itemSku, String itemName, InventoryCategory category, InventoryUnit unit, Double reorderLevel, Double currentQuantity, List<StockEntity> stocks, Boolean isScalable) {
        this.inventoryId = inventoryId;
        this.itemSku = itemSku;
        this.itemName = itemName;
        this.category = category;
        this.unit = unit;
        this.reorderLevel = reorderLevel;
        this.currentQuantity = currentQuantity;
        this.stocks = stocks;
        this.isScalable = isScalable;
    }

    public Long getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(Long inventoryId) {
        this.inventoryId = inventoryId;
    }

    public String getItemSku() {
        return itemSku;
    }

    public void setItemSku(String itemSku) {
        this.itemSku = itemSku;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public InventoryCategory getCategory() {
        return category;
    }

    public void setCategory(InventoryCategory category) {
        this.category = category;
    }

    public InventoryUnit getUnit() {
        return unit;
    }

    public void setUnit(InventoryUnit unit) {
        this.unit = unit;
    }

    public Double getReorderLevel() {
        return reorderLevel;
    }

    public void setReorderLevel(Double reorderLevel) {
        this.reorderLevel = reorderLevel;
    }

    public Double getCurrentQuantity() {
        return currentQuantity;
    }

    public void setCurrentQuantity(Double currentQuantity) {
        this.currentQuantity = currentQuantity;
    }

    public List<StockEntity> getStocks() {
        return stocks;
    }

    public void setStocks(List<StockEntity> stocks) {
        this.stocks = stocks;
    }

    public Boolean getIsScalable() {
        return isScalable;
    }

    public void setIsScalable(Boolean scalable) {
        isScalable = scalable;
    }
}
