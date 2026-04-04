package com.example.CakeShopManagement.controller;

import com.example.CakeShopManagement.dto.ProductsDto;
import com.example.CakeShopManagement.service.ProductRegistrationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class ProductRegistrationController {

    ProductRegistrationService productRegistrationService;

    public ProductRegistrationController(ProductRegistrationService productRegistrationService) {
        this.productRegistrationService = productRegistrationService;
    }

//    @PostMapping(value = "/product-registration")
//    public ResponseEntity<ProductsDto> addForm(@RequestBody ProductsDto productsDto) {
//        try {
//            ProductsDto productsDtoResponse = productRegistrationService.addProductEntity(productsDto);
//            return ResponseEntity.created(URI.create("/product-registration" + productsDtoResponse.getProductId())).body(productsDtoResponse);
//        }
//        catch (Exception e){
//            throw new AppException("Request failed with error: "+e, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//
//    }


    @PostMapping("/product-registration")
    public ResponseEntity<ProductsDto> addProduct(@ModelAttribute ProductsDto productsDto) throws IOException {
        ProductsDto productsDto1 = productRegistrationService.addProduct(productsDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(productsDto1);
    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductsDto>> getAllProducts() {
        List<ProductsDto> productsDtos = productRegistrationService.getAllProducts();
//        System.out.println("*********************"+productRegistrationService.getAllProducts().get(1).getSize());
        return ResponseEntity.ok(productsDtos);
    }

    @GetMapping("/search/{productName}")
    public ResponseEntity<List<ProductsDto>> getAllProductByName(@PathVariable String productName) {
        List<ProductsDto> productsDtos = productRegistrationService.getAllProductsByName(productName);
        return ResponseEntity.ok(productsDtos);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<ProductsDto> getProductById(@PathVariable long productId) {
        ProductsDto productsDto = productRegistrationService.getProductById(productId);
        if(productsDto != null) {
            return ResponseEntity.ok(productsDto);
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/product/{productId}")
    public ResponseEntity<ProductsDto> updateProduct(@PathVariable Long productId, @ModelAttribute ProductsDto productsDto) throws IOException {
        ProductsDto updateProduct = productRegistrationService.updateProduct(productId, productsDto);
        if(updateProduct != null) {
            return ResponseEntity.ok(updateProduct);
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("product/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long productId) {
        boolean deleted = productRegistrationService.deleteProduct(productId);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }


}
