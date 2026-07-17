package com.example.CakeShopManagement.service;

import com.example.CakeShopManagement.dto.ProductVariantDto;
import com.example.CakeShopManagement.dto.ProductsDto;

import java.io.IOException;
import java.util.List;

public interface ProductRegistrationService {


//    ProductsDto addProductEntity(ProductsDto productsDto);
    ProductsDto addProduct(ProductsDto productsDto, String customizations) throws IOException;

//    ProductsDto addImage(ProductsDto productsDto) throws IOException;

//    List<ProductsDto> getAllProducts();
    List<ProductsDto> getAllProducts();

    List<ProductsDto> getAllProductsByName(String productName);

    ProductsDto getProductById(Long productId);

    List<ProductVariantDto> getProductVariants(Long productId);

    ProductsDto updateProduct(Long productId, ProductsDto productsDto, String customizations) throws IOException;

    boolean deleteProduct(Long productId);

    boolean getProductBySku(String productSku);

    String generateNextSku();
}
