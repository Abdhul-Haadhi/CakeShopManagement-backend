package com.example.CakeShopManagement.service.Impl;

import com.example.CakeShopManagement.dto.AddToCartDto;
import com.example.CakeShopManagement.dto.CartItemDto;
import com.example.CakeShopManagement.entity.CartItemCustomizationEntity;
import com.example.CakeShopManagement.entity.CartItemsEntity;
import com.example.CakeShopManagement.entity.ProductEntity;
import com.example.CakeShopManagement.repository.CartItemCustomizationRepository;
import com.example.CakeShopManagement.repository.CartRepository;
import com.example.CakeShopManagement.repository.CustomizationOptionRepository;
import com.example.CakeShopManagement.repository.ProductRegistrationRepository;
import com.example.CakeShopManagement.service.AddToCartService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddToCartServiceImpl implements AddToCartService {

    private final ProductRegistrationRepository productRegistrationRepository;
    private final CartRepository cartRepository;
    private final CartItemCustomizationRepository cartItemCustomizationRepository;
    private final CustomizationOptionRepository customizationOptionRepository;

    public AddToCartServiceImpl(ProductRegistrationRepository productRegistrationRepository, CartRepository cartRepository, CartItemCustomizationRepository cartItemCustomizationRepository, CustomizationOptionRepository customizationOptionRepository) {
        this.productRegistrationRepository = productRegistrationRepository;
        this.cartRepository = cartRepository;
        this.cartItemCustomizationRepository = cartItemCustomizationRepository;
        this.customizationOptionRepository = customizationOptionRepository;
    }


    public CartItemDto addToCart(AddToCartDto addToCartDto) {

        ProductEntity productEntity = productRegistrationRepository.findById(addToCartDto.getProductId()).orElseThrow();

        CartItemsEntity cartItemsEntity = new CartItemsEntity();

        cartItemsEntity.setSessionId(addToCartDto.getSessionId());
        cartItemsEntity.setProductEntity(productEntity);
        cartItemsEntity.setQuantity(addToCartDto.getQuantity());

        long customizationPrice = 0;

        if(addToCartDto.getCustomizations() != null){
            for(var c : addToCartDto.getCustomizations()){
                customizationPrice += c.getExtraPrice();
            }
        }

        long finalPrice = (productEntity.getPrice() + customizationPrice) * addToCartDto.getQuantity();

        cartItemsEntity.setPrice(finalPrice);

        CartItemsEntity saved = cartRepository.save(cartItemsEntity);

        if(addToCartDto.getCustomizations() != null){
            for (var custom : addToCartDto.getCustomizations()) {

                System.out.println("OPTION ID = " + custom.getOptionId());
                System.out.println("VALUE = " + custom.getValue());
                System.out.println("PRICE = " + custom.getExtraPrice());

                CartItemCustomizationEntity customization = new CartItemCustomizationEntity();

                customization.setCartItem(saved);

                customization.setOption(customizationOptionRepository.findById(custom.getOptionId()).orElseThrow());

                customization.setSelectedValue(String.valueOf(custom.getValue()));

                customization.setExtraPrice(java.math.BigDecimal.valueOf(custom.getExtraPrice()));

                cartItemCustomizationRepository.save(customization);
            }
        }
        return saved.getDto();
    }

    public List<CartItemDto> getCart(String sessionId){
        return cartRepository.findBySessionIdAndOrderIsNull(sessionId)
                .stream()
                .map(CartItemsEntity::getDto)
                .toList();
    }


    @Override
    public CartItemDto getCartItemById(Long id){
        return cartRepository.findById(id)
                .orElseThrow()
                .getDto();
    }

    public void deleteCartItem(Long cartId){
        cartRepository.deleteById(cartId);
    }
}
