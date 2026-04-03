package com.example.CakeShopManagement.service;

import com.example.CakeShopManagement.dto.ProductsDto;

import java.io.IOException;
import java.util.List;

public interface ProductRegistrationService {


//    ProductsDto addProductEntity(ProductsDto productsDto);
    ProductsDto addProduct(ProductsDto productsDto) throws IOException;

//    ProductsDto addImage(ProductsDto productsDto) throws IOException;

//    List<ProductsDto> getAllProducts();
    List<ProductsDto> getAllProducts();

    List<ProductsDto> getAllProductsByName(String productName);

    boolean deleteProduct(Long productId);
}
