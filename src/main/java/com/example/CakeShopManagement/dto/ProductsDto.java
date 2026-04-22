package com.example.CakeShopManagement.dto;


import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class ProductsDto {


    private Long productId;
    private String productSku;
    private String productName;
    private String description;
//    private List<String> colors;
    private int size;
    private int quantity;
    private int price;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime addedDate;
    private byte[] byteImage;
    private MultipartFile image;

    private Long categoryId;
    private String categoryName;


    public ProductsDto() {
    }

    public ProductsDto(Long productId, String productSku, String productName, String description, int size, int quantity, int price, LocalDateTime addedDate, byte[] byteImage, MultipartFile image, Long categoryId, String categoryName) {
        this.productId = productId;
        this.productSku = productSku;
        this.productName = productName;
        this.description = description;
        this.size = size;
        this.quantity = quantity;
        this.price = price;
        this.addedDate = addedDate;
        this.byteImage = byteImage;
        this.image = image;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductSku() {
        return productSku;
    }

    public void setProductSku(String productSku) {
        this.productSku = productSku;
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

    public LocalDateTime getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(LocalDateTime addedDate) {
        this.addedDate = addedDate;
    }

    public byte[] getByteImage() {
        return byteImage;
    }

    public void setByteImage(byte[] byteImage) {
        this.byteImage = byteImage;
    }

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
