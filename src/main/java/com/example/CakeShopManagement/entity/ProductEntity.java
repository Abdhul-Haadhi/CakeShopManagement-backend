package com.example.CakeShopManagement.entity;

import com.example.CakeShopManagement.dto.ProductCustomizationDto;
import com.example.CakeShopManagement.dto.ProductVariantDto;
import com.example.CakeShopManagement.dto.ProductsDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "product")
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;
    @Column(nullable = false, unique = true)
    private String productSku;
    private String productName;
    @Lob
    private String description;
//    private int size;
//    private int quantity;
//    private int price;
    private LocalDate addedDate;
    private Boolean active = true;

    @Lob
    @Column(columnDefinition = "longblob")
    private byte[] image;

    @OneToMany(mappedBy = "product",cascade = CascadeType.ALL)
    private List<ProductCustomizationEntity> customizations;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductVariant> variants;

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
        productsDto.setProductSku(productSku);
        productsDto.setProductName(productName);
        productsDto.setDescription(description);
        productsDto.setAddedDate(addedDate);
        productsDto.setActive(active);
//        productsDto.setQuantity(quantity);
        productsDto.setByteImage(image);
        productsDto.setCategoryId(categoryEntity.getCategoryId());
        productsDto.setCategoryName(categoryEntity.getCategoryName());

        if(variants != null){
            List<ProductVariantDto> variantDtos = variants.stream()
                    .map(v -> new ProductVariantDto(
                            v.getVariantId(),
                            v.getWeight(),
                            v.getPrice())).toList();

            productsDto.setVariants(variantDtos);
        }

//        if(customizations != null){
//            List<ProductCustomizationDto> customizationDtos = customizations.stream().map(pc->new ProductCustomizationDto(
//                    pc.getCustomizationOption().getOptionId(),
//                    pc.getCustomizationOption().getOptionName(),
//                    pc.getCustomizationOption().getOptionType(),
//                    pc.getExtraPrice()
//            ))
//                    .toList();
//            productsDto.setCustomizations(customizationDtos);
//        }

        if(customizations != null){
            List<ProductCustomizationDto> customizationDtos = customizations.stream().map(pc -> {
                ProductCustomizationDto dto = new ProductCustomizationDto();
                dto.setOptionId(pc.getCustomizationOption().getOptionId());
                dto.setOptionName(pc.getCustomizationOption().getOptionName());
                dto.setOptionType(pc.getCustomizationOption().getOptionType());
                dto.setExtraPrice(pc.getExtraPrice());
                dto.setOptionValues(
                        pc.getCustomizationOption()
                                .getValues()
                                .stream()
                                .map(CustomizationOptionValueEntity::getValue)
                                .toList()
                );
                return dto;
            }).toList();

            productsDto.setCustomizations(customizationDtos);

//            if(productSizes != null){
//                productsDto.setSizes(
//                        productSizes.stream().map(size -> new ProductSizeDto(
//                                size.getSizeId(),
//                                size.getSize(),
//                                size.getPrice())).toList()
//                );
//            }
        }

        return productsDto;
    }

    public ProductEntity() {
    }

    public ProductEntity(Long productId, String productSku, String productName, String description, LocalDate addedDate, Boolean active, byte[] image, List<ProductCustomizationEntity> customizations, List<ProductVariant> variants, CategoryEntity categoryEntity) {
        this.productId = productId;
        this.productSku = productSku;
        this.productName = productName;
        this.description = description;
        this.addedDate = addedDate;
        this.active = active;
        this.image = image;
        this.customizations = customizations;
        this.variants = variants;
        this.categoryEntity = categoryEntity;
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

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public List<ProductCustomizationEntity> getCustomizations() {
        return customizations;
    }

    public void setCustomizations(List<ProductCustomizationEntity> customizations) {
        this.customizations = customizations;
    }

    public List<ProductVariant> getVariants() {
        return variants;
    }

    public void setVariants(List<ProductVariant> variants) {
        this.variants = variants;
    }

    public CategoryEntity getCategoryEntity() {
        return categoryEntity;
    }

    public void setCategoryEntity(CategoryEntity categoryEntity) {
        this.categoryEntity = categoryEntity;
    }
}
