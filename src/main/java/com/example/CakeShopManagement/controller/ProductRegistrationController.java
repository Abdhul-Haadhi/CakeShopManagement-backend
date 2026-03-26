package com.example.CakeShopManagement.controller;

import com.example.CakeShopManagement.dto.ProductsDto;
import com.example.CakeShopManagement.exceptions.AppException;
import com.example.CakeShopManagement.service.ProductRegistrationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/api/admin")
public class ProductRegistrationController {

    ProductRegistrationService productRegistrationService;

    public ProductRegistrationController(ProductRegistrationService productRegistrationService) {
        this.productRegistrationService = productRegistrationService;
    }

    @PostMapping(value = "/product-registration")
    public ResponseEntity<ProductsDto> addForm(@RequestBody ProductsDto productsDto) {
        try {
            ProductsDto productsDtoResponse = productRegistrationService.addProductEntity(productsDto);
            return ResponseEntity.created(URI.create("/product-registration" + productsDtoResponse.getProductId())).body(productsDtoResponse);
        }
        catch (Exception e){
            throw new AppException("Request failed with error: "+e, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
