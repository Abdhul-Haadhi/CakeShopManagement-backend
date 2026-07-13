package com.example.CakeShopManagement.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "recipe",uniqueConstraints = {@UniqueConstraint(columnNames = {"product_id","inventory_id"})})
public class RecipeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recipeId;

    @ManyToOne
    @JoinColumn(name = "product_id",nullable = false)
    private ProductEntity product;

    @ManyToOne
    @JoinColumn(name="inventory_id", nullable = false)
    private InventoryEntity inventory;

    @Column(nullable = false)
    private Double quantityRequired;

    public RecipeEntity() {
    }

    public RecipeEntity(Long recipeId, ProductEntity product, InventoryEntity inventory, Double quantityRequired) {
        this.recipeId = recipeId;
        this.product = product;
        this.inventory = inventory;
        this.quantityRequired = quantityRequired;
    }

    public Long getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(Long recipeId) {
        this.recipeId = recipeId;
    }

    public ProductEntity getProduct() {
        return product;
    }

    public void setProduct(ProductEntity product) {
        this.product = product;
    }

    public InventoryEntity getInventory() {
        return inventory;
    }

    public void setInventory(InventoryEntity inventory) {
        this.inventory = inventory;
    }

    public Double getQuantityRequired() {
        return quantityRequired;
    }

    public void setQuantityRequired(Double quantityRequired) {
        this.quantityRequired = quantityRequired;
    }
}
