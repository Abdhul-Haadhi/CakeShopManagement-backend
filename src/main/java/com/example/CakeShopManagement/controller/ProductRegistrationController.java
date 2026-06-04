package com.example.CakeShopManagement.controller;

import com.example.CakeShopManagement.dto.ProductsDto;
import com.example.CakeShopManagement.repository.ProductRegistrationRepository;
import com.example.CakeShopManagement.service.ProductRegistrationService;
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
    public ResponseEntity<ProductsDto> addProduct( @RequestParam("productSku") String productSku,
                                                   @RequestParam("productName") String productName,
                                                   @RequestParam("description") String description,
                                                   @RequestParam("size") int size,
                                                   @RequestParam("quantity") int quantity,
                                                   @RequestParam("price") int price,
                                                   @RequestParam("addedDate") String addedDate,
                                                   @RequestParam("categoryId") Long categoryId,
                                                   @RequestParam(value = "image", required = false) MultipartFile image,
                                                   @RequestParam(value = "customizations", required = false) String customizations)throws IOException{
        ProductsDto dto = new ProductsDto();

        dto.setProductSku(productSku);
        dto.setProductName(productName);
        dto.setDescription(description);
        dto.setSize(size);
        dto.setQuantity(quantity);
        dto.setPrice(price);
        dto.setAddedDate(LocalDate.parse(addedDate));
        dto.setCategoryId(categoryId);
        dto.setImage(image);

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

//    @GetMapping("/check-sku")
//    public ResponseEntity<Boolean> checkSkuExists(@RequestParam String productSku) {
//        boolean exists = productRegistrationRepository.existsByProductSku(productSku);
//        return ResponseEntity.ok(exists);
//    }


//    @GetMapping("/product/{productSku}")
//    public ResponseEntity<ProductsDto> getProductBySku(@PathVariable String productSku) {
//        boolean getSku = productRegistrationService.getProductBySku(productSku);
//        return ResponseEntity.ok();
//    }

    @GetMapping("/check-sku/{productSku}")
    public ResponseEntity<Boolean> checkSkuExists( @PathVariable String productSku) {
        return ResponseEntity.ok(productRegistrationService.getProductBySku(productSku));
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

    @DeleteMapping("/product/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long productId) {
        boolean deleted = productRegistrationService.deleteProduct(productId);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }


}
