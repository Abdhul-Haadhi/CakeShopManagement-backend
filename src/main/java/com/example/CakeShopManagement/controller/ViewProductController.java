package com.example.CakeShopManagement.controller;


import com.example.CakeShopManagement.dto.ProductsDto;
import com.example.CakeShopManagement.service.ProductRegistrationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/public")
public class ViewProductController {

    ProductRegistrationService productRegistrationService;

    public ViewProductController(ProductRegistrationService productRegistrationService) {
        this.productRegistrationService = productRegistrationService;
    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductsDto>> getAllProducts() {
        List<ProductsDto> productsDtos = productRegistrationService.getAllProducts();
        return ResponseEntity.ok(productsDtos);
    }

    @GetMapping("/search/{productName}")
    public ResponseEntity<List<ProductsDto>> getAllProductByName(@PathVariable String productName) {
        List<ProductsDto> productsDtos = productRegistrationService.getAllProductsByName(productName);
        return ResponseEntity.ok(productsDtos);
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<ProductsDto> getProductById(@PathVariable Long id) {
        ProductsDto productsDto = productRegistrationService.getProductById(id);

        return ResponseEntity.ok(productsDto);
    }
}
