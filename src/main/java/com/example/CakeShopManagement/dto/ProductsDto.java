package com.example.CakeShopManagement.dto;


import com.example.CakeShopManagement.entity.ProductVariant;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class ProductsDto {


    private Long productId;
    private String productSku;
    private String productName;
    private String description;
    private String sellingType;
//    private List<String> colors;
//    private int size;
//    private int quantity;
//    private int price;
    private LocalDate addedDate;
    private Boolean active;
    private byte[] byteImage;
    private MultipartFile image;

    private Long categoryId;
    private String categoryName;

    private List<ProductCustomizationDto> customizations;
    private List<ProductVariantDto> variants;



    public ProductsDto() {
    }

    public ProductsDto(Long productId, String productSku, String productName, String description, String sellingType, LocalDate addedDate, Boolean active, byte[] byteImage, MultipartFile image, Long categoryId, String categoryName, List<ProductCustomizationDto> customizations, List<ProductVariantDto> variants) {
        this.productId = productId;
        this.productSku = productSku;
        this.productName = productName;
        this.description = description;
        this.sellingType = sellingType;
        this.addedDate = addedDate;
        this.active = active;
        this.byteImage = byteImage;
        this.image = image;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.customizations = customizations;
        this.variants = variants;
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

    public String getSellingType() {
        return sellingType;
    }

    public void setSellingType(String sellingType) {
        this.sellingType = sellingType;
    }

    public LocalDate getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(LocalDate addedDate) {
        this.addedDate = addedDate;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
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

    public List<ProductCustomizationDto> getCustomizations() {
        return customizations;
    }

    public void setCustomizations(List<ProductCustomizationDto> customizations) {
        this.customizations = customizations;
    }

    public List<ProductVariantDto> getVariants() {
        return variants;
    }

    public void setVariants(List<ProductVariantDto> variants) {
        this.variants = variants;
    }
}
