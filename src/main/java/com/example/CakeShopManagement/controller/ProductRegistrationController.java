package com.example.CakeShopManagement.controller;

import com.example.CakeShopManagement.dto.ProductReportDto;
import com.example.CakeShopManagement.dto.ProductVariantDto;
import com.example.CakeShopManagement.dto.ProductsDto;
import com.example.CakeShopManagement.repository.ProductRegistrationRepository;
import com.example.CakeShopManagement.service.ProductRegistrationService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/employee")
public class ProductRegistrationController {

    ProductRegistrationService productRegistrationService;
    ProductRegistrationRepository productRegistrationRepository;

    public ProductRegistrationController(ProductRegistrationService productRegistrationService) {
        this.productRegistrationService = productRegistrationService;
    }


//    @PostMapping("/product-registration")
//    public ResponseEntity<ProductsDto> addProduct(@ModelAttribute ProductsDto productsDto) throws IOException {
//        ProductsDto productsDto1 = productRegistrationService.addProduct(productsDto);
//        return ResponseEntity.status(HttpStatus.CREATED).body(productsDto1);
//    }

//    @PostMapping("/product-registration")
//    public ResponseEntity<ProductsDto> addProduct(@ModelAttribute ProductsDto productsDto,
//                                                  @RequestParam(value = "customizations", required = false)
//                                                  String customizations) throws IOException {
//        ProductsDto productsDto1 = productRegistrationService.addProduct(productsDto,customizations);
//
//        return ResponseEntity.status(HttpStatus.CREATED).body(productsDto1);
//    }

    @PostMapping("/product-registration")
    public ResponseEntity<ProductsDto> addProduct( @RequestParam("productName") String productName,
                                                   @RequestParam("description") String description,
                                                   @RequestParam("sellingType") String sellingType,
//                                                   @RequestParam("quantity") int quantity,
//                                                   @RequestParam("addedDate") String addedDate,
                                                   @RequestParam("variants") String variants,
                                                   @RequestParam("categoryId") Long categoryId,
                                                   @RequestParam(value = "image", required = false) MultipartFile image,
                                                   @RequestParam(value = "customizations", required = false) String customizations)throws IOException{
        ProductsDto dto = new ProductsDto();

//        dto.setProductSku(productSku);
        dto.setProductName(productName);
        dto.setDescription(description);
        dto.setSellingType(sellingType);
//        dto.setQuantity(quantity);
//        dto.setAddedDate(LocalDate.parse(addedDate));
        dto.setCategoryId(categoryId);
        dto.setImage(image);

        List<ProductVariantDto> variantDtos = new ObjectMapper().readValue(variants, new TypeReference<List<ProductVariantDto>>() {
        });
        dto.setVariants(variantDtos);

        ProductsDto saved = productRegistrationService.addProduct(dto,customizations);

        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
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

    @GetMapping("/check-sku/{productSku}")
    public ResponseEntity<Boolean> checkSkuExists( @PathVariable String productSku) {
        return ResponseEntity.ok(productRegistrationService.getProductBySku(productSku));
    }

    @GetMapping("/next-product-sku")
    public ResponseEntity<String> getNextSku(){
        return ResponseEntity.ok(productRegistrationService.generateNextSku());
    }

    @GetMapping("/product/{id}/variants")
    public ResponseEntity<List<ProductVariantDto>> getVariants(@PathVariable Long id){

        return ResponseEntity.ok(productRegistrationService.getProductVariants(id));

    }

    @GetMapping("/product-report")
    public ResponseEntity<List<ProductReportDto>> getProductReport(@RequestParam(required = false) Long categoryId) {
        List<ProductReportDto> report = productRegistrationService.getProductReport(categoryId);
        return ResponseEntity.ok(report);
    }

//    @PutMapping("/product/{productId}")
//    public ResponseEntity<ProductsDto> updateProduct(@PathVariable Long productId, @ModelAttribute ProductsDto productsDto, @RequestParam(value = "customizations",required = false)String customizations) throws IOException {
//
//        System.out.println("**************UPDATE CALLED");
//        System.out.println("ID = " + productId);
//        System.out.println("NAME = " + productsDto.getProductName());
//
//        ProductsDto updateProduct = productRegistrationService.updateProduct(productId, productsDto,customizations);
//        if(updateProduct != null) {
//            return ResponseEntity.ok(updateProduct);
//        }
//        else {
//            return ResponseEntity.notFound().build();
//        }
//    }

    @PutMapping(value = "/product/{productId}", consumes = "multipart/form-data")
    public ResponseEntity<ProductsDto> updateProduct(@PathVariable Long productId,
                                                     @RequestParam("productName") String productName,
                                                     @RequestParam("description") String description,
                                                     @RequestParam("sellingType") String sellingType,
//                                                     @RequestParam("quantity") int quantity,
//                                                     @RequestParam("addedDate") String addedDate,
                                                     @RequestParam("variants") String variants,
                                                     @RequestParam("categoryId") Long categoryId,
                                                     @RequestParam(value = "image", required = false)
                                                         MultipartFile image,
                                                     @RequestParam(value = "customizations", required = false)
                                                         String customizations
                                                     ) throws IOException {

        ProductsDto dto = new ProductsDto();

        dto.setProductName(productName);
        dto.setDescription(description);
        dto.setSellingType(sellingType);
//        dto.setQuantity(quantity);
//        dto.setAddedDate(LocalDate.parse(addedDate));
        dto.setCategoryId(categoryId);
        dto.setImage(image);

        List<ProductVariantDto> variantDtos =
                new ObjectMapper().readValue(
                        variants,
                        new TypeReference<List<ProductVariantDto>>() {}
                );
        dto.setVariants(variantDtos);

        ProductsDto updateProduct = productRegistrationService.updateProduct(productId, dto, customizations);

        return ResponseEntity.ok(updateProduct);
    }

    @DeleteMapping("/product/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long productId) {
        boolean deleted = productRegistrationService.deleteProduct(productId);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }


}
