package com.example.CakeShopManagement.entity;

import com.example.CakeShopManagement.dto.ProductsDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "product")
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;
    private String productName;
    @Lob
    private String description;
    private int size;
    private int quantity;
    private int price;
//    private LocalDateTime createdAt;
    @Lob
    @Column(columnDefinition = "longblob")
    private byte[] image;





//
//    private String imageName;
//    private String imageType;

//    @ManyToMany
//    @JoinTable(
//            name = "Product_category",
//            joinColumns = @JoinColumn(name = "productId"),
//            inverseJoinColumns = @JoinColumn(name = "categoryId")
//    )
//    @OnDelete(action = OnDeleteAction.CASCADE)
//    @JsonIgnore
//    private List<CategoryEntity> categories;

    @ManyToOne(fetch = FetchType.LAZY, optional=false)
    @JoinColumn(name = "category_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private CategoryEntity categoryEntity;


    public ProductsDto getDto(){
        ProductsDto productsDto = new ProductsDto();
        productsDto.setProductId(productId);
        productsDto.setProductName(productName);
        productsDto.setDescription(description);
        productsDto.setPrice(price);
        productsDto.setSize(size);
        productsDto.setQuantity(quantity);
        productsDto.setByteImage(image);
        productsDto.setCategoryId(categoryEntity.getCategoryId());
        productsDto.setCategoryName(categoryEntity.getCategoryName());

        return productsDto;
    }

    public ProductEntity() {
    }


    public ProductEntity(Long productId, String productName, String description, int size, int quantity, int price, byte[] image, CategoryEntity categoryEntity) {
        this.productId = productId;
        this.productName = productName;
        this.description = description;
        this.size = size;
        this.quantity = quantity;
        this.price = price;
        this.image = image;
        this.categoryEntity = categoryEntity;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public CategoryEntity getCategoryEntity() {
        return categoryEntity;
    }

    public void setCategoryEntity(CategoryEntity categoryEntity) {
        this.categoryEntity = categoryEntity;
    }
}
