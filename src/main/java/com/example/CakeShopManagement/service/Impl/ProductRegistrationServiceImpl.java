package com.example.CakeShopManagement.service.Impl;

import com.example.CakeShopManagement.dto.ProductCustomizationDto;
import com.example.CakeShopManagement.dto.ProductsDto;
import com.example.CakeShopManagement.entity.CategoryEntity;
import com.example.CakeShopManagement.entity.CustomizationOptionEntity;
import com.example.CakeShopManagement.entity.ProductCustomizationEntity;
import com.example.CakeShopManagement.entity.ProductEntity;
import com.example.CakeShopManagement.exceptions.AppException;
import com.example.CakeShopManagement.mappers.ProductRegistrationMapper;
import com.example.CakeShopManagement.repository.CategoryRepository;
import com.example.CakeShopManagement.repository.CustomizationOptionRepository;
import com.example.CakeShopManagement.repository.ProductCustomizationRepository;
import com.example.CakeShopManagement.repository.ProductRegistrationRepository;
import com.example.CakeShopManagement.service.ProductRegistrationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.core.type.TypeReference;


import java.io.IOException;
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

    public ProductRegistrationServiceImpl(ProductRegistrationRepository productRegistrationRepository, ProductRegistrationMapper productRegistrationMapper, CategoryRepository categoryRepository, ProductCustomizationRepository productCustomizationRepository, CustomizationOptionRepository customizationOptionRepository, ObjectMapper objectMapper) {
        this.productRegistrationRepository = productRegistrationRepository;
//        this.productRegistrationMapper = productRegistrationMapper;
        this.categoryRepository = categoryRepository;
        this.productCustomizationRepository = productCustomizationRepository;
        this.customizationOptionRepository = customizationOptionRepository;
        this.objectMapper = objectMapper;
    }


//    @Override
//    public ProductsDto addProductEntity(ProductsDto productsDto) {
//        try {
//            ProductEntity productEntity = productRegistrationMapper.toProductEntity(productsDto);
//
//            if(productsDto.getImage() != null){
//                productEntity.setImage(productsDto.getImage().getBytes());
//            }
//            ProductEntity savedItem = productRegistrationRepository.save(productEntity);
//            ProductsDto savedDto = productRegistrationMapper.toProductDto(savedItem);
//            return savedDto;
//
//        }
//        catch (Exception e) {
//            throw new AppException("Request failed with error: "+ e, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

//    public ProductsDto addProduct(ProductsDto productsDto) throws IOException {
//
//        if(productRegistrationRepository.existsByProductSku(productsDto.getProductSku())){
//            throw new AppException("Product SKU already exists!",HttpStatus.BAD_REQUEST);
//        }
//
//        ProductEntity productEntity = new ProductEntity();
//        productEntity.setProductSku(productsDto.getProductSku());
//        productEntity.setProductName(productsDto.getProductName());
//        productEntity.setDescription(productsDto.getDescription());
//        productEntity.setSize(productsDto.getSize());
//        productEntity.setQuantity(productsDto.getQuantity());
//        productEntity.setPrice(productsDto.getPrice());
//        productEntity.setAddedDate(productsDto.getAddedDate());
//        productEntity.setImage(productsDto.getImage().getBytes());
//
//        CategoryEntity categoryEntity = categoryRepository.findById(productsDto.getCategoryId()).orElseThrow();
//        productEntity.setCategoryEntity(categoryEntity);
//
//        return productRegistrationRepository.save(productEntity).getDto();
//    }

    @Override
    public ProductsDto addProduct(ProductsDto productsDto,String customizations) throws IOException {

        System.out.println("SKU RECEIVED = " + productsDto.getProductSku());

        if(productRegistrationRepository.existsByProductSku(productsDto.getProductSku())){
            throw new AppException("Product SKU already exists!",HttpStatus.BAD_REQUEST);
        }

        ProductEntity productEntity = new ProductEntity();

        productEntity.setProductSku(productsDto.getProductSku());
        productEntity.setProductName(productsDto.getProductName());
        productEntity.setDescription(productsDto.getDescription());
        productEntity.setSize(productsDto.getSize());
        productEntity.setQuantity(productsDto.getQuantity());
        productEntity.setPrice(productsDto.getPrice());
        productEntity.setAddedDate(productsDto.getAddedDate());

        if(productsDto.getImage() != null){
            productEntity.setImage(productsDto.getImage().getBytes());
        }

        CategoryEntity categoryEntity = categoryRepository.findById(productsDto.getCategoryId()).orElseThrow();

        productEntity.setCategoryEntity(categoryEntity);

        ProductEntity saveProduct = productRegistrationRepository.save(productEntity);

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
        List<ProductEntity> productEntities = productRegistrationRepository.findAll();
//        System.out.println("*********************"+ productEntities.get(1).getSize());
        return productEntities.stream().map(ProductEntity::getDto).collect(Collectors.toList());
    }

    public List<ProductsDto> getAllProductsByName(String productName) {
        List<ProductEntity> productEntities = productRegistrationRepository.findAllByProductNameContaining(productName);
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

    public boolean getProductBySku(String productSku) {
//        System.out.println("********************"+productSku);
        return productRegistrationRepository.existsByProductSku(productSku);

    }

    public ProductsDto updateProduct(Long productId, ProductsDto productsDto) throws IOException {
        Optional<ProductEntity> optionalProductEntity = productRegistrationRepository.findById(productId);
        Optional<CategoryEntity> optionalCategoryEntity = categoryRepository.findById(productsDto.getCategoryId());

        if(optionalProductEntity.isPresent() && optionalCategoryEntity.isPresent()){
            ProductEntity productEntity = optionalProductEntity.get();

            productEntity.setProductName(productsDto.getProductName());
            productEntity.setDescription(productsDto.getDescription());
            productEntity.setSize(productsDto.getSize());
            productEntity.setQuantity(productsDto.getQuantity());
            productEntity.setPrice(productsDto.getPrice());
            productEntity.setAddedDate(productsDto.getAddedDate());
            productEntity.setCategoryEntity(optionalCategoryEntity.get());
            if(productsDto.getImage() != null){
                productEntity.setImage(productsDto.getImage().getBytes());
            }
            return productRegistrationRepository.save(productEntity).getDto();
        }
        else {
            return null;
        }
    }

    public boolean deleteProduct(Long productId) {
        Optional<ProductEntity> optionalProduct = productRegistrationRepository.findById(productId);
        if(optionalProduct.isPresent()) {
            productRegistrationRepository.deleteById(productId);
            return true;
        }
        return false;
    }
}
