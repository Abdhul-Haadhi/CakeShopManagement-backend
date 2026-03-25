package com.example.CakeShopManagement.service.Impl;

import com.example.CakeShopManagement.dto.ProductsDto;
import com.example.CakeShopManagement.entity.ProductEntity;
import com.example.CakeShopManagement.exceptions.AppException;
import com.example.CakeShopManagement.mappers.ProductRegistrationMapper;
import com.example.CakeShopManagement.repository.ProductRegistrationRepository;
import com.example.CakeShopManagement.service.ProductRegistrationService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;


@Service
public class ProductRegistrationServiceImpl implements ProductRegistrationService {

    private final ProductRegistrationRepository productRegistrationRepository;
    private final ProductRegistrationMapper productRegistrationMapper;

    public ProductRegistrationServiceImpl(ProductRegistrationRepository productRegistrationRepository, ProductRegistrationMapper productRegistrationMapper) {
        this.productRegistrationRepository = productRegistrationRepository;
        this.productRegistrationMapper = productRegistrationMapper;
    }


    @Override
    public ProductsDto addProductEntity(ProductsDto productsDto) {
        try {
            ProductEntity productEntity = productRegistrationMapper.toProductEntity(productsDto);
            ProductEntity savedItem = productRegistrationRepository.save(productEntity);
            ProductsDto savedDto = productRegistrationMapper.toProductDto(savedItem);
            return savedDto;
        }
        catch (Exception e) {
            throw new AppException("Request failed with error: "+ e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
