package com.example.CakeShopManagement.service;


import com.example.CakeShopManagement.dto.AddToCartDto;
import com.example.CakeShopManagement.entity.CartItemsEntity;

import java.util.List;

public interface AddToCartService {
    CartItemsEntity addToCart(AddToCartDto addToCartDto);
    List<CartItemsEntity> getCart(String sessionId);
    void deleteCartItem(Long cartId);
    CartItemsEntity getCartItemById(Long id);
}
