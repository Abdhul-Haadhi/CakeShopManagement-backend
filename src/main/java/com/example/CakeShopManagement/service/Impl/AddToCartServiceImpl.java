package com.example.CakeShopManagement.service.Impl;

import com.example.CakeShopManagement.dto.AddToCartDto;
import com.example.CakeShopManagement.entity.CartItemsEntity;
import com.example.CakeShopManagement.entity.ProductEntity;
import com.example.CakeShopManagement.repository.CartRepository;
import com.example.CakeShopManagement.repository.ProductRegistrationRepository;
import com.example.CakeShopManagement.service.AddToCartService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddToCartServiceImpl implements AddToCartService {

    private final ProductRegistrationRepository productRegistrationRepository;
    private final CartRepository cartRepository;

    public AddToCartServiceImpl(ProductRegistrationRepository productRegistrationRepository, CartRepository cartRepository) {
        this.productRegistrationRepository = productRegistrationRepository;
        this.cartRepository = cartRepository;
    }


    public CartItemsEntity addToCart(AddToCartDto addToCartDto) {
        ProductEntity productEntity = productRegistrationRepository.findById(addToCartDto.getProductId()).orElseThrow();

        CartItemsEntity cartItemsEntity = new CartItemsEntity();

        cartItemsEntity.setSessionId(addToCartDto.getSessionId());
        cartItemsEntity.setProductEntity(productEntity);
        cartItemsEntity.setQuantity(addToCartDto.getQuantity());
        cartItemsEntity.setPrice(productEntity.getPrice() * addToCartDto.getQuantity());

        return cartRepository.save(cartItemsEntity);
    }

    public List<CartItemsEntity> getCart(String sessionId){
        return cartRepository.findBySessionId(sessionId);
    }

    public void deleteCartItem(Long cartId){
        cartRepository.deleteById(cartId);
    }
}
