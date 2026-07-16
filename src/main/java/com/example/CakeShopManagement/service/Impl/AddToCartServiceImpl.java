package com.example.CakeShopManagement.service.Impl;

import com.example.CakeShopManagement.dto.AddToCartDto;
import com.example.CakeShopManagement.dto.CartItemDto;
import com.example.CakeShopManagement.entity.*;
import com.example.CakeShopManagement.repository.*;
import com.example.CakeShopManagement.service.AddToCartService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddToCartServiceImpl implements AddToCartService {

    private final ProductRegistrationRepository productRegistrationRepository;
    private final CartRepository cartRepository;
    private final CartItemCustomizationRepository cartItemCustomizationRepository;
    private final CustomizationOptionRepository customizationOptionRepository;
    private final CustomerRepository customerRepository;
    private final ProductVariantRepository productVariantRepository;

    public AddToCartServiceImpl(ProductRegistrationRepository productRegistrationRepository, CartRepository cartRepository, CartItemCustomizationRepository cartItemCustomizationRepository, CustomizationOptionRepository customizationOptionRepository, CustomerRepository customerRepository, ProductVariantRepository productVariantRepository) {
        this.productRegistrationRepository = productRegistrationRepository;
        this.cartRepository = cartRepository;
        this.cartItemCustomizationRepository = cartItemCustomizationRepository;
        this.customizationOptionRepository = customizationOptionRepository;
        this.customerRepository = customerRepository;
        this.productVariantRepository = productVariantRepository;
    }


    public CartItemDto addToCart(AddToCartDto addToCartDto) {

        ProductEntity productEntity = productRegistrationRepository.findById(addToCartDto.getProductId()).orElseThrow();

        CartItemsEntity cartItemsEntity = new CartItemsEntity();

        if(addToCartDto.getCustomerId() != null){
            CustomerEntity customer = customerRepository.findById(addToCartDto.getCustomerId()).orElseThrow(()->new RuntimeException("Customer Not Found"));

            cartItemsEntity.setCustomer(customer);
        }
        else {
            cartItemsEntity.setSessionId(addToCartDto.getSessionId());
        }

//        ProductVariant variant = productVariantRepository.findById(addToCartDto.getVariantId()).orElseThrow();

        ProductVariant variant = productVariantRepository.findById(addToCartDto.getVariantId()).orElseThrow();
        cartItemsEntity.setWeight(variant.getWeight());
//        cartItemsEntity.setUnitPrice(
//                variant.getPrice().longValue()
//        );


        cartItemsEntity.setProductEntity(productEntity);
//        cartItemsEntity.setProductVariant(variant);
        cartItemsEntity.setQuantity(addToCartDto.getQuantity());

        long customizationPrice = 0;

        if(addToCartDto.getCustomizations() != null){
            for(var c : addToCartDto.getCustomizations()){
                customizationPrice += c.getExtraPrice();
            }
        }



//        long finalPrice = (variant.getPrice().longValue() + customizationPrice) * addToCartDto.getQuantity();

        long unitPrice = variant.getPrice().longValue() + customizationPrice;
        cartItemsEntity.setUnitPrice(unitPrice);
        long finalPrice = unitPrice * addToCartDto.getQuantity();

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

                if(custom.getReferenceImage() != null){
                    customization.setReferenceImage(custom.getReferenceImage());
                }

                customization.setExtraPrice(java.math.BigDecimal.valueOf(custom.getExtraPrice()));

                cartItemCustomizationRepository.save(customization);
            }
        }
        return saved.getDto();
    }

    public List<CartItemDto> getCart(String sessionId, Long customerId){

        if(customerId != null){
            return cartRepository.findByCustomerCustomerIdAndOrderIsNull(customerId).stream().map(CartItemsEntity::getDto).toList();
        }

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


    @Override
    public void mergeGuestCartToCustomer(String sessionId, Long customerId){
        CustomerEntity customer = customerRepository.findById(customerId).orElseThrow(()->new RuntimeException("Customer Not Found"));

        List<CartItemsEntity> guestCartItems = cartRepository.findBySessionIdAndCustomerIsNullAndOrderIsNull(sessionId);

        List<CartItemsEntity> customerCartItems = cartRepository.findByCustomerCustomerIdAndOrderIsNull(customerId);

        for(CartItemsEntity guestItem : guestCartItems){

            boolean alreadyExists = false;

            for(CartItemsEntity customerItem : customerCartItems){
                if(customerItem.getProductEntity().getProductId().equals(guestItem.getProductEntity().getProductId())
                && customerItem.getUnitPrice().equals(guestItem.getUnitPrice()))
                {
                    customerItem.setQuantity(customerItem.getQuantity() + guestItem.getQuantity());
                    customerItem.setPrice(customerItem.getPrice() + guestItem.getPrice());

                    cartRepository.save(customerItem);
                    cartRepository.save(guestItem);

                    alreadyExists = true;
                    break;
                }
            }
            if(!alreadyExists){
                if(guestItem.getCustomer() != null && guestItem.getCustomer().getCustomerId().equals(customerId)){
                    continue;
                }
                guestItem.setCustomer(customer);
                guestItem.setSessionId(null);

                cartRepository.save(guestItem);
            }

        }
    }


    @Override
    public CartItemDto updateQuantity(Long cartId, Long quantity) {
        CartItemsEntity cartItem = cartRepository.findById(cartId).orElseThrow();

        long customizationPrice = cartItem.getCustomizations()
                .stream()
                .mapToLong(c ->
                        c.getExtraPrice().longValue()).sum();

//        ProductVariant variant = cartItem.getProductVariant();
//
//        long unitPrice = variant.getPrice().longValue() + customizationPrice;

        long unitPrice = cartItem.getUnitPrice();
        cartItem.setPrice(
                unitPrice * quantity
        );

        long newPrice = unitPrice * quantity;

        cartItem.setQuantity(quantity);
        cartItem.setPrice(newPrice);

        CartItemsEntity saved = cartRepository.save(cartItem);

        return saved.getDto();
    }

    public void deleteCartItem(Long cartId){
        cartRepository.deleteById(cartId);
    }
}
