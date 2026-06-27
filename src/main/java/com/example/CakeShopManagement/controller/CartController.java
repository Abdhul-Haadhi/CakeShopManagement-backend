package com.example.CakeShopManagement.controller;


import com.example.CakeShopManagement.dto.AddToCartDto;
import com.example.CakeShopManagement.dto.UpdateCartQuantityDto;
import com.example.CakeShopManagement.service.AddToCartService;
import com.example.CakeShopManagement.service.Impl.AddToCartServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/public")
@RestController
public class CartController {

    private final AddToCartServiceImpl addToCartServiceImpl;
    private final AddToCartService addToCartService;

    public CartController(AddToCartServiceImpl addToCartServiceImpl, AddToCartService addToCartService) {
        this.addToCartServiceImpl = addToCartServiceImpl;
        this.addToCartService = addToCartService;
    }

    @PostMapping("/cart")
    public ResponseEntity<?> addToCart(@RequestBody AddToCartDto addToCartDto) {
        return ResponseEntity.ok(addToCartService.addToCart(addToCartDto));
    }


    @GetMapping("/cart")
    public ResponseEntity<?> getCart(@RequestParam(required = false) String sessionId,
                                     @RequestParam(required = false) Long customerId) {
        return ResponseEntity.ok(addToCartService.getCart(sessionId, customerId));
    }

    @GetMapping("/cart/item/{id}")
    public ResponseEntity<?> getCartItemById(@PathVariable Long id){
        return ResponseEntity.ok(addToCartService.getCartItemById(id));
    }

    @PostMapping("/cart/merge")
    public ResponseEntity<?> mergeCart(@RequestParam String sessionId, @RequestParam Long customerId) {
        addToCartService.mergeGuestCartToCustomer(sessionId, customerId);
        return ResponseEntity.ok("Cart merged successfully");
    }

    @PutMapping("/cart/{cartId}/quantity")
    public ResponseEntity<?> updateQuantity(@PathVariable Long cartId, @RequestBody UpdateCartQuantityDto dto) {
        System.out.println("**********************************");
        return ResponseEntity.ok(addToCartService.updateQuantity(cartId,dto.getQuantity()));
    }

    @DeleteMapping("/cart/{cartId}")
    public ResponseEntity<?> deleteCartItem(@PathVariable Long cartId){
        addToCartService.deleteCartItem(cartId);
        return ResponseEntity.ok().build();
    }


}
