package com.example.CakeShopManagement.controller;


import com.example.CakeShopManagement.dto.ProductsDto;
import com.example.CakeShopManagement.service.ProductRegistrationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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

    @PostMapping("/upload/customization-image")
    public ResponseEntity<?> uploadCustomizationImage(@RequestParam("file") MultipartFile file) throws Exception {
        String fileName = UUID.randomUUID() + file.getOriginalFilename();

        Path uploadPath = Paths.get("uploads/customizations");

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        Files.copy(file.getInputStream(),
                uploadPath.resolve(fileName),
                StandardCopyOption.REPLACE_EXISTING);

        String imageUrl = "/uploads/customizations/" + fileName;

        return ResponseEntity.ok(Map.of("imageUrl", imageUrl));
    }
}
