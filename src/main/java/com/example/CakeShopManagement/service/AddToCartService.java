package com.example.CakeShopManagement.service;


import com.example.CakeShopManagement.dto.AddToCartDto;
import com.example.CakeShopManagement.dto.CartItemDto;

import java.util.List;

public interface AddToCartService {
    CartItemDto addToCart(AddToCartDto addToCartDto);
    List<CartItemDto> getCart(String sessionId);
    void deleteCartItem(Long cartId);
    CartItemDto getCartItemById(Long id);
}
