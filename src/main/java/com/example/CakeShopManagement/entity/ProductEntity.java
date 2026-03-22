package com.example.CakeShopManagement.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "product")
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long productId;
    private String productName;
    private Long price;

    @Lob
    private String description;

    private int size;
    private int quantity;
    private LocalDateTime createdAt;

    @Lob
    @Column(columnDefinition = "longblob")
    private byte[] image;

    @ManyToMany
    @JoinTable(
            name = "Product_category",
            joinColumns = @JoinColumn(name = "productId"),
            inverseJoinColumns = @JoinColumn(name = "categoryId")
    )
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private List<CategoryEntity> categories;

    public ProductEntity() {
    }

    public ProductEntity(long productId, String productName, Long price, String description, int size, int quantity, LocalDateTime createdAt, byte[] image, List<CategoryEntity> categories) {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.description = description;
        this.size = size;
        this.quantity = quantity;
        this.createdAt = createdAt;
        this.image = image;
        this.categories = categories;
    }


    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public List<CategoryEntity> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryEntity> categories) {
        this.categories = categories;
    }
}
