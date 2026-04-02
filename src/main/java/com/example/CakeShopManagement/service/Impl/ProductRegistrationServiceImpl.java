package com.example.CakeShopManagement.service.Impl;

import com.example.CakeShopManagement.dto.ProductsDto;
import com.example.CakeShopManagement.entity.CategoryEntity;
import com.example.CakeShopManagement.entity.ProductEntity;
import com.example.CakeShopManagement.exceptions.AppException;
import com.example.CakeShopManagement.mappers.ProductRegistrationMapper;
import com.example.CakeShopManagement.repository.CategoryRepository;
import com.example.CakeShopManagement.repository.ProductRegistrationRepository;
import com.example.CakeShopManagement.service.ProductRegistrationService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;


@Service
public class ProductRegistrationServiceImpl implements ProductRegistrationService {

    private final ProductRegistrationRepository productRegistrationRepository;
    private final ProductRegistrationMapper productRegistrationMapper;
    private final CategoryRepository categoryRepository;

    public ProductRegistrationServiceImpl(ProductRegistrationRepository productRegistrationRepository, ProductRegistrationMapper productRegistrationMapper, CategoryRepository categoryRepository) {
        this.productRegistrationRepository = productRegistrationRepository;
        this.productRegistrationMapper = productRegistrationMapper;
        this.categoryRepository = categoryRepository;
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

    public ProductsDto addProduct(ProductsDto productsDto) throws IOException {
        ProductEntity productEntity = new ProductEntity();
        productEntity.setProductName(productsDto.getProductName());
        productEntity.setDescription(productsDto.getDescription());
        productEntity.setSize(productsDto.getSize());
        productEntity.setQuantity(productsDto.getQuantity());
        productEntity.setPrice(productsDto.getPrice());
        productEntity.setImage(productsDto.getImage().getBytes());

        CategoryEntity categoryEntity = categoryRepository.findById(productsDto.getCategoryId()).orElseThrow();
        productEntity.setCategoryEntity(categoryEntity);

        return productRegistrationRepository.save(productEntity).getDto();
    }

    public List<ProductsDto> getAllProducts() {
        List<ProductEntity> productEntities = productRegistrationRepository.findAll();
        return productEntities.stream().map(ProductEntity::getDto).collect(Collectors.toList());
    }

    public List<ProductsDto> getAllProductsByName(String productName) {
        List<ProductEntity> productEntities = productRegistrationRepository.findAllByProductNameContaining(productName);
        return productEntities.stream().map(ProductEntity::getDto).collect(Collectors.toList());
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
