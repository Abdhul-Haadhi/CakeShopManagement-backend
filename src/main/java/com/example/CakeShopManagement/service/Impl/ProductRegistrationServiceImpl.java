package com.example.CakeShopManagement.service.Impl;

import com.example.CakeShopManagement.dto.ProductCustomizationDto;
import com.example.CakeShopManagement.dto.ProductReportDto;
import com.example.CakeShopManagement.dto.ProductVariantDto;
import com.example.CakeShopManagement.dto.ProductsDto;
import com.example.CakeShopManagement.entity.*;
import com.example.CakeShopManagement.enums.VariantType;
import com.example.CakeShopManagement.mappers.ProductRegistrationMapper;
import com.example.CakeShopManagement.repository.*;
import com.example.CakeShopManagement.service.ProductRegistrationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.transaction.annotation.Transactional;


import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class ProductRegistrationServiceImpl implements ProductRegistrationService {

    private final ProductRegistrationRepository productRegistrationRepository;
//    private final ProductRegistrationMapper productRegistrationMapper;
    private final CategoryRepository categoryRepository;

    private final ProductCustomizationRepository productCustomizationRepository;
    private final CustomizationOptionRepository customizationOptionRepository;
    private final ObjectMapper objectMapper;
    private final ProductVariantRepository productVariantRepository;


    public ProductRegistrationServiceImpl(ProductRegistrationRepository productRegistrationRepository, ProductRegistrationMapper productRegistrationMapper, CategoryRepository categoryRepository, ProductCustomizationRepository productCustomizationRepository, CustomizationOptionRepository customizationOptionRepository, ObjectMapper objectMapper, ProductVariantRepository productVariantRepository) {
        this.productRegistrationRepository = productRegistrationRepository;
//        this.productRegistrationMapper = productRegistrationMapper;
        this.categoryRepository = categoryRepository;
        this.productCustomizationRepository = productCustomizationRepository;
        this.customizationOptionRepository = customizationOptionRepository;
        this.objectMapper = objectMapper;
        this.productVariantRepository = productVariantRepository;
    }


    @Override
    public ProductsDto addProduct(ProductsDto productsDto,String customizations) throws IOException {

//        System.out.println("SKU RECEIVED = " + productsDto.getProductSku());
//
//        if(productRegistrationRepository.existsByProductSku(productsDto.getProductSku())){
//            throw new AppException("Product SKU already exists!",HttpStatus.BAD_REQUEST);
//        }

        String generatedSku = generateNextSku();

        while (productRegistrationRepository.existsByProductSku(generatedSku)){
            generatedSku = generateNextSku();
        }

        ProductEntity productEntity = new ProductEntity();

//        productEntity.setProductSku(productsDto.getProductSku());
        productEntity.setProductSku(generatedSku);
        productEntity.setProductName(productsDto.getProductName());
        productEntity.setDescription(productsDto.getDescription());
        productEntity.setSellingType(productsDto.getSellingType());
//        productEntity.setQuantity(productsDto.getQuantity());
        productEntity.setAddedDate(java.time.LocalDate.now());

        if(productsDto.getImage() != null){
            productEntity.setImage(productsDto.getImage().getBytes());
        }

        CategoryEntity categoryEntity = categoryRepository.findById(productsDto.getCategoryId()).orElseThrow();

        productEntity.setCategoryEntity(categoryEntity);

        productEntity.setActive(true);

        ProductEntity saveProduct = productRegistrationRepository.save(productEntity);

        List<ProductVariantDto> variantDtos = productsDto.getVariants();

        if(variantDtos != null){
            for(ProductVariantDto dto : variantDtos){

                ProductVariant variant = new ProductVariant();

                variant.setProduct(saveProduct);
//                variant.setWeight(dto.getWeight());
//                variant.setPrice(dto.getPrice());

                variant.setVariantType(VariantType.valueOf(dto.getVariantType()));
                variant.setWeight(dto.getWeight());
                variant.setPieces(dto.getPieces());
                variant.setPrice(dto.getPrice());

                productVariantRepository.save(variant);
            }
        }


//        if(productsDto.getSizes() != null){
//            for(ProductSizeDto dto : productsDto.getSizes()){
//                ProductSizeEntity size = new ProductSizeEntity();
//                size.setProduct(saveProduct);
//                size.setSize(dto.getSize());
//                size.setPrice(dto.getPrice());
//
//                productSizeRepository.save(size);
//            }
//        }

        if(customizations != null && !customizations.isEmpty()){
            List<ProductCustomizationDto> customizationDtos = objectMapper.readValue(customizations,
                    new TypeReference<List<ProductCustomizationDto>>() {
                    });

            for(ProductCustomizationDto dto : customizationDtos){
                ProductCustomizationEntity customization1 = new ProductCustomizationEntity();

                customization1.setProduct(saveProduct);

                CustomizationOptionEntity option = customizationOptionRepository.findById(dto.getOptionId()).orElseThrow();

                customization1.setCustomizationOption(option);
                customization1.setExtraPrice(dto.getExtraPrice());

                productCustomizationRepository.save(customization1);
            }
        }

        return saveProduct.getDto();
    }

    public List<ProductsDto> getAllProducts() {
        List<ProductEntity> productEntities = productRegistrationRepository.findByActiveTrue();
//        System.out.println("*********************"+ productEntities.get(1).getSize());
        return productEntities.stream().map(ProductEntity::getDto).collect(Collectors.toList());
    }

    public List<ProductsDto> getAllProductsByName(String productName) {
        List<ProductEntity> productEntities = productRegistrationRepository.findByActiveTrueAndProductNameContaining(productName);
        return productEntities.stream().map(ProductEntity::getDto).collect(Collectors.toList());
    }

    public ProductsDto getProductById(Long productId) {
        Optional<ProductEntity> optionalProductEntity = productRegistrationRepository.findById(productId);
        if(optionalProductEntity.isPresent()){
            return optionalProductEntity.get().getDto();
        }
        else {
            return null;
        }
    }

    @Override
    public List<ProductVariantDto> getProductVariants(Long productId) {

        return productVariantRepository
                .findByProductProductId(productId)
                .stream()
                .map(v -> {

                    ProductVariantDto dto = new ProductVariantDto();

                    dto.setVariantId(v.getVariantId());
                    dto.setVariantType(v.getVariantType().toString());

                    dto.setWeight(v.getWeight());
                    dto.setPieces(v.getPieces());

                    return dto;

                }).toList();
    }

    public String generateNextSku() {

        ProductEntity lastProduct = productRegistrationRepository.findTopByOrderByProductIdDesc();

        if (lastProduct == null) {
            return "PRD0001";
        }

        String lastSku = lastProduct.getProductSku();

        // Remove the "PRD" prefix
        int number = Integer.parseInt(lastSku.substring(3));

        number++;

        // Add the prefix back
        return "PRD" + String.format("%04d", number);

//        Long count = productRegistrationRepository.count() + 1;
//
//        return "PRD" + String.format("%04d", count);
    }

    public boolean getProductBySku(String productSku) {
//        System.out.println("********************"+productSku);
        return productRegistrationRepository.existsByProductSku(productSku);

    }


    @Override
    public List<ProductReportDto> getProductReport(Long categoryId) {
        List<ProductEntity> products = productRegistrationRepository.findAll();

        return products.stream()
                .filter(p -> categoryId == null || (p.getCategoryEntity() != null && p.getCategoryEntity().getCategoryId().equals(categoryId)))
                .map(product -> {
                    ProductReportDto dto = new ProductReportDto();
                    dto.setProductId(product.getProductId());
                    dto.setProductSku(product.getProductSku());
                    dto.setProductName(product.getProductName());

                    if (product.getCategoryEntity() != null) {
                        dto.setCategoryId(product.getCategoryEntity().getCategoryId());
                        dto.setCategoryName(product.getCategoryEntity().getCategoryName());
                    }

                    dto.setActive(product.getActive());
                    dto.setAddedDate(product.getAddedDate());

                    List<ProductVariant> variants = product.getVariants();
                    int variantCount = (variants != null) ? variants.size() : 0;
                    dto.setVariantCount(variantCount);

                    if (variants != null && !variants.isEmpty()) {
                        // Map to ProductVariantDto instead of VariantDto
                        List<ProductReportDto.ProductVariantDto> variantDtos = variants.stream()
                                .map(v -> new ProductReportDto.ProductVariantDto(
                                        v.getVariantId(),
                                        v.getVariantType().name(),
                                        v.getWeight() != null ? v.getWeight() + " g" : "N/A",
                                        v.getPrice() != null ? v.getPrice() : 0.0,
                                        v.getAvailable()
                                ))
                                .collect(Collectors.toList());

                        dto.setVariants(variantDtos);

                        double min = variants.stream().mapToDouble(v -> v.getPrice() != null ? v.getPrice() : 0.0).min().orElse(0.0);
                        double max = variants.stream().mapToDouble(v -> v.getPrice() != null ? v.getPrice() : 0.0).max().orElse(0.0);

                        dto.setMinPrice(min);
                        dto.setMaxPrice(max);

                        if (min == max) {
                            dto.setPriceDisplay(String.format("Rs.%.2f", min));
                        } else {
                            dto.setPriceDisplay(String.format("Rs.%.2f - Rs.%.2f", min, max));
                        }

                        boolean hasAvailableVariant = variants.stream().anyMatch(v -> Boolean.TRUE.equals(v.getAvailable()));

                        if (!Boolean.TRUE.equals(product.getActive())) {
                            dto.setAvailabilityStatus("Inactive");
                        } else if (hasAvailableVariant) {
                            dto.setAvailabilityStatus("In Stock");
                        } else {
                            dto.setAvailabilityStatus("Out of Stock");
                        }
                    } else {
                        dto.setVariants(Collections.emptyList());
                        dto.setMinPrice(0.0);
                        dto.setMaxPrice(0.0);
                        dto.setPriceDisplay("N/A");
                        dto.setAvailabilityStatus(Boolean.TRUE.equals(product.getActive()) ? "In Stock" : "Inactive");
                    }

                    return dto;
                })
                .collect(Collectors.toList());
    }



    @Transactional
    @Override
    public ProductsDto updateProduct(Long productId, ProductsDto productsDto,String customizations) throws IOException {

//        System.out.println("**********SERVICE HIT");
        Optional<ProductEntity> optionalProductEntity = productRegistrationRepository.findById(productId);
        Optional<CategoryEntity> optionalCategoryEntity = categoryRepository.findById(productsDto.getCategoryId());

        if(optionalProductEntity.isPresent() && optionalCategoryEntity.isPresent()){
            ProductEntity productEntity = optionalProductEntity.get();

            productEntity.setProductName(productsDto.getProductName());
            productEntity.setDescription(productsDto.getDescription());
//            productEntity.setQuantity(productsDto.getQuantity());
//            productEntity.setAddedDate(productsDto.getAddedDate());
            productEntity.setCategoryEntity(optionalCategoryEntity.get());
            if(productsDto.getImage() != null){
                productEntity.setImage(productsDto.getImage().getBytes());
            }

            ProductEntity updateProduct = productRegistrationRepository.save(productEntity);

            productVariantRepository.deleteByProductProductId(productId);
            if(productsDto.getVariants() != null){
                for(ProductVariantDto dto : productsDto.getVariants()){
                    ProductVariant variant = new ProductVariant();

                    variant.setProduct(updateProduct);
//                    variant.setWeight(dto.getWeight());
//                    variant.setPrice(dto.getPrice());

                    variant.setVariantType(VariantType.valueOf(dto.getVariantType()));
                    variant.setWeight(dto.getWeight());
                    variant.setPieces(dto.getPieces());
                    variant.setPrice(dto.getPrice());

                    productVariantRepository.save(variant);
                }
            }

//            if(productsDto.getSizes() != null){
//                for(ProductSizeDto dto : productsDto.getSizes()){
//                    ProductSizeEntity size = new ProductSizeEntity();
//                    size.setProduct(updateProduct);
//                    size.setSize(dto.getSize());
//                    size.setPrice(dto.getPrice());
//
//                    productSizeRepository.save(size);
//                }
//            }

            System.out.println(
                    "Before delete: " +
                            productCustomizationRepository.findByProductProductId(productId).size()
            );

            productCustomizationRepository.deleteByProductProductId(productId);

            System.out.println(
                    "After delete: " +
                            productCustomizationRepository.findByProductProductId(productId).size()
            );

            if(customizations != null && !customizations.isEmpty()){
                List<ProductCustomizationDto> customizationDtos = objectMapper.readValue(customizations, new TypeReference<List<ProductCustomizationDto>>() {});

                for (ProductCustomizationDto dto : customizationDtos) {
                    ProductCustomizationEntity customization = new ProductCustomizationEntity();

                    customization.setProduct(updateProduct);

                    CustomizationOptionEntity option = customizationOptionRepository.findById(dto.getOptionId()).orElseThrow();

                    customization.setCustomizationOption(option);

                    customization.setExtraPrice(dto.getExtraPrice());

                    productCustomizationRepository.save(customization);
                }
            }

            return updateProduct.getDto();
        }
        else {
            return null;
        }
    }

//    public boolean deleteProduct(Long productId) {
//        Optional<ProductEntity> optionalProduct = productRegistrationRepository.findById(productId);
//        if(optionalProduct.isPresent()) {
//            productRegistrationRepository.deleteById(productId);
//            return true;
//        }
//        return false;
//    }

    public boolean deleteProduct(Long productId) {
        Optional<ProductEntity> optionalProduct = productRegistrationRepository.findById(productId);
        if(optionalProduct.isPresent()) {
            ProductEntity product = optionalProduct.get();
            product.setActive(false);
            productRegistrationRepository.save(product);
            return true;
        }
        return false;
    }
}
