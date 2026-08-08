package com.example.CakeShopManagement.dto;

import java.time.LocalDate;
import java.util.List;

public class ProductReportDto {

    private Long productId;
    private String productSku;
    private String productName;
    private Long categoryId;
    private String categoryName;
    private Double minPrice;
    private Double maxPrice;
    private String priceDisplay; // e.g., "$15.00" or "$10.00 - $25.00"
    private Boolean active;
    private String availabilityStatus; // 'In Stock', 'Out of Stock', 'Inactive'
    private Integer variantCount;
    private LocalDate addedDate;

    private List<ProductVariantDto> variants;

    public ProductReportDto() {
    }

    public ProductReportDto(Long productId, String productSku, String productName, Long categoryId, String categoryName, Double minPrice, Double maxPrice, String priceDisplay, Boolean active, String availabilityStatus, Integer variantCount, LocalDate addedDate, List<ProductVariantDto> variants) {
        this.productId = productId;
        this.productSku = productSku;
        this.productName = productName;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.priceDisplay = priceDisplay;
        this.active = active;
        this.availabilityStatus = availabilityStatus;
        this.variantCount = variantCount;
        this.addedDate = addedDate;
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

    public Double getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(Double minPrice) {
        this.minPrice = minPrice;
    }

    public Double getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(Double maxPrice) {
        this.maxPrice = maxPrice;
    }

    public String getPriceDisplay() {
        return priceDisplay;
    }

    public void setPriceDisplay(String priceDisplay) {
        this.priceDisplay = priceDisplay;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getAvailabilityStatus() {
        return availabilityStatus;
    }

    public void setAvailabilityStatus(String availabilityStatus) {
        this.availabilityStatus = availabilityStatus;
    }

    public Integer getVariantCount() {
        return variantCount;
    }

    public void setVariantCount(Integer variantCount) {
        this.variantCount = variantCount;
    }

    public LocalDate getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(LocalDate addedDate) {
        this.addedDate = addedDate;
    }

    public List<ProductVariantDto> getVariants() {
        return variants;
    }

    public void setVariants(List<ProductVariantDto> variants) {
        this.variants = variants;
    }

    public static class ProductVariantDto {
        private Long variantId;
        private String variantName;
        private String weight;
        private Double price;
        private Boolean available;

        public ProductVariantDto() {}

        public ProductVariantDto(Long variantId, String variantName, String weight, Double price, Boolean available) {
            this.variantId = variantId;
            this.variantName = variantName;
            this.weight = weight;
            this.price = price;
            this.available = available;
        }

        public Long getVariantId() { return variantId; }
        public void setVariantId(Long variantId) { this.variantId = variantId; }

        public String getVariantName() { return variantName; }
        public void setVariantName(String variantName) { this.variantName = variantName; }

        public String getWeight() { return weight; }
        public void setWeight(String weight) { this.weight = weight; }

        public Double getPrice() { return price; }
        public void setPrice(Double price) { this.price = price; }

        public Boolean getAvailable() { return available; }
        public void setAvailable(Boolean available) { this.available = available; }
    }
}
