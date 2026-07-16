package com.example.CakeShopManagement.service.Impl;

import com.example.CakeShopManagement.dto.OrderHistoryDto;
import com.example.CakeShopManagement.dto.OrderItemCustomizationDto;
import com.example.CakeShopManagement.dto.OrderItemDto;
import com.example.CakeShopManagement.dto.PlaceOrderDto;
import com.example.CakeShopManagement.entity.*;
import com.example.CakeShopManagement.exceptions.AppException;
import com.example.CakeShopManagement.mappers.OrderMapper;
import com.example.CakeShopManagement.repository.*;
import com.example.CakeShopManagement.service.InventoryConsumptionService;
import com.example.CakeShopManagement.service.OrderService;
import com.example.CakeShopManagement.service.PaymentService;
import com.example.CakeShopManagement.service.RecipeService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final CartRepository cartRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrderItemCustomizationRepository orderItemCustomizationRepository;
    private final PaymentRepository paymentRepository;
    private final PaymentService paymentService;
    private final CustomerRepository customerRepository;
    private final RecipeService recipeService;
    private final InventoryConsumptionService inventoryConsumptionService;

    public OrderServiceImpl(OrderRepository orderRepository, OrderMapper orderMapper, CartRepository cartRepository, OrderItemRepository orderItemRepository, OrderItemCustomizationRepository orderItemCustomizationRepository, PaymentRepository paymentRepository, PaymentService paymentService, CustomerRepository customerRepository, RecipeService recipeService, InventoryConsumptionService inventoryConsumptionService) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
        this.cartRepository = cartRepository;
        this.orderItemRepository = orderItemRepository;
        this.orderItemCustomizationRepository = orderItemCustomizationRepository;
        this.paymentRepository = paymentRepository;
        this.paymentService = paymentService;
        this.customerRepository = customerRepository;
        this.recipeService = recipeService;
        this.inventoryConsumptionService = inventoryConsumptionService;
    }

    @Override
    public PlaceOrderDto placeOrder(PlaceOrderDto placeOrderDto) {
        try {
            OrderEntity orderEntity = orderMapper.toOrderEntity(placeOrderDto);

            PaymentEntity existingPayment = null;

            if("CARD".equals(placeOrderDto.getPaymentMethod())
                    && placeOrderDto.getPaymentId() != null){

                existingPayment = paymentRepository.findById(placeOrderDto.getPaymentId())
                        .orElseThrow(()->new RuntimeException("Payment not found"));

//                orderEntity.setPayment(payment);

            }

            orderEntity.setOrderDate(new java.util.Date());
            orderEntity.setStatus("PENDING");
            orderEntity.setTrackingId(java.util.UUID.randomUUID().toString());
            orderEntity.setPaymentMethod(placeOrderDto.getPaymentMethod());

            if(placeOrderDto.getCustomerId() != null){
                CustomerEntity customer = customerRepository.findById(placeOrderDto.getCustomerId()).orElseThrow(()->new RuntimeException("Customer not found"));

                orderEntity.setCustomer(customer);

                // optional: no need session for logged users
                orderEntity.setSessionId(null);
            }

            OrderEntity savedOrder  = orderRepository.save(orderEntity);

            if(existingPayment != null){
                existingPayment.setOrder(savedOrder);
                paymentRepository.save(existingPayment);
            }

            if(placeOrderDto.getPaymentMethod().equals("COD")){
                PaymentEntity payment = new PaymentEntity();

                payment.setPaymentMethod("COD");
                payment.setPaymentStatus("PENDING");
                payment.setTransactionId(null);
                payment.setAmount(placeOrderDto.getTotalAmount().doubleValue());
                payment.setGatewayName("Cash On Delivery");
                payment.setOrder(savedOrder);

                paymentRepository.save(payment);
            }

            List<CartItemsEntity> cartItems = cartRepository.findAllById(placeOrderDto.getCartItemIds());

            for(CartItemsEntity cartItem:cartItems){

                OrderItemEntity orderItem = new OrderItemEntity();

                orderItem.setOrder(savedOrder);
                orderItem.setProduct(cartItem.getProductEntity());
                orderItem.setQuantity(cartItem.getQuantity());
//                orderItem.setPrice(cartItem.getProductEntity().getPrice() * cartItem.getQuantity());
                orderItem.setPrice(cartItem.getPrice());

                OrderItemEntity savedOrderItem = orderItemRepository.save(orderItem);

                for(CartItemCustomizationEntity cartCustom : cartItem.getCustomizations()){
                    OrderItemCustomizationEntity orderCustom  = new OrderItemCustomizationEntity();

                    orderCustom.setOrderItem(savedOrderItem);
                    orderCustom.setOption(cartCustom.getOption());
                    orderCustom.setSelectedValue(cartCustom.getSelectedValue());
                    if(cartCustom.getReferenceImage() != null){
                        orderCustom.setReferenceImage(cartCustom.getReferenceImage());
                    }
                    orderCustom.setExtraPrice(cartCustom.getExtraPrice());

                    orderItemCustomizationRepository.save(orderCustom);
                }
            }

            cartRepository.deleteAll(cartItems);

            return orderMapper.toPlaceOrderDto(savedOrder);
        }
        catch (Exception e) {
            throw new AppException("Request failed with error: "+e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    @Override
//    public List<OrderEntity> getOrdersBySession(String sessionId) {
//        return orderRepository.findBySessionIdOrderByOrderDateDesc(sessionId);
//    }

    @Override
    public List<OrderHistoryDto> getOrdersBySession(String sessionId) {
        return orderRepository.findBySessionIdOrderByOrderDateDesc(sessionId)
                .stream()
                .map(order -> {
                    OrderHistoryDto dto = new OrderHistoryDto();

                    dto.setOrderId(order.getOrderId());
                    dto.setTotalAmount(order.getTotalAmount());
                    dto.setQuantity(order.getQuantity());
                    dto.setStatus(order.getStatus());
                    dto.setTrackingId(order.getTrackingId());
                    dto.setOrderDate(order.getOrderDate());
                    dto.setDeliveryDate(order.getDeliveryDate());

                    List<OrderItemDto> items = order.getOrderItems().stream()
                            .map(item -> {
                                OrderItemDto itemDto = new OrderItemDto();

                                itemDto.setOrderItemId(item.getOrderItemId());
                                itemDto.setQuantity(item.getQuantity());
                                itemDto.setPrice(item.getPrice());
                                itemDto.setProductId(item.getProduct().getProductId());
                                itemDto.setProductName(item.getProduct().getProductName());

                                List<OrderItemCustomizationDto> customizationDtos =
                                        item.getCustomizations()
                                                .stream()
                                                .map(custom->{
                                                    OrderItemCustomizationDto dto1 = new OrderItemCustomizationDto();

                                                    dto1.setOptionId(custom.getOption().getOptionId());

                                                    dto1.setOptionName(custom.getOption().getOptionName());

                                                    dto1.setValue(custom.getSelectedValue());

                                                    dto1.setExtraPrice(custom.getExtraPrice().longValue());

                                                    dto1.setReferenceImage(custom.getReferenceImage());

                                                    dto1.setOptionType(custom.getOption().getOptionType());

                                                    return dto1;
                                                }).toList();
                                itemDto.setCustomizations(customizationDtos);
                                return itemDto;
                            }).toList();
                    dto.setOrderItems(items);
                    return dto;
                }).toList();
    }

    @Override
    public List<OrderHistoryDto> getOrders(Long customerId, String sessionId) {
        List<OrderEntity> orders;

        if (customerId != null) {
            orders = orderRepository.findByCustomerCustomerIdOrderByOrderDateDesc(customerId);
        } else {
            orders = orderRepository.findBySessionIdOrderByOrderDateDesc(sessionId);
        }

        return orders.stream()
                .map(order -> {
                    OrderHistoryDto dto = new OrderHistoryDto();

                    dto.setOrderId(order.getOrderId());
                    dto.setTotalAmount(order.getTotalAmount());
                    dto.setQuantity(order.getQuantity());
                    dto.setStatus(order.getStatus());
                    dto.setTrackingId(order.getTrackingId());
                    dto.setOrderDate(order.getOrderDate());
                    dto.setDeliveryDate(order.getDeliveryDate());

                    dto.setCustomerName(order.getCustomerName());
                    dto.setPhone(order.getPhone());
                    dto.setEmail(order.getEmail());
                    dto.setAddress(order.getAddress());
                    dto.setCity(order.getCity());
                    dto.setPaymentMethod(order.getPaymentMethod());

                    List<OrderItemDto> items = order.getOrderItems().stream()
                            .map(item -> {
                                OrderItemDto itemDto = new OrderItemDto();

                                itemDto.setOrderItemId(item.getOrderItemId());
                                itemDto.setQuantity(item.getQuantity());
                                itemDto.setPrice(item.getPrice());
                                itemDto.setProductId(item.getProduct().getProductId());
                                itemDto.setProductName(item.getProduct().getProductName());

                                List<OrderItemCustomizationDto> customizationDtos =
                                        item.getCustomizations().stream()
                                                .map(custom -> {
                                                    OrderItemCustomizationDto customDto =
                                                            new OrderItemCustomizationDto();

                                                    customDto.setOptionId(custom.getOption().getOptionId());
                                                    customDto.setOptionName(custom.getOption().getOptionName());
                                                    customDto.setValue(custom.getSelectedValue());
                                                    customDto.setExtraPrice(custom.getExtraPrice().longValue());
                                                    customDto.setOptionType(custom.getOption().getOptionType());
                                                    customDto.setReferenceImage(custom.getReferenceImage());

                                                    return customDto;
                                                }).toList();

                                itemDto.setCustomizations(customizationDtos);

                                return itemDto;
                            }).toList();

                    dto.setOrderItems(items);

                    return dto;
                }).toList();
    }

    @Override
    public List<OrderHistoryDto> getAllOrders(){
        return orderRepository.findAllByOrderByOrderDateDesc()
                .stream()
                .map(order -> {
                    OrderHistoryDto dto = new OrderHistoryDto();

                    dto.setOrderId(order.getOrderId());
                    dto.setTotalAmount(order.getTotalAmount());
                    dto.setQuantity(order.getQuantity());
                    dto.setStatus(order.getStatus());
                    dto.setTrackingId(order.getTrackingId());
                    dto.setOrderDate(order.getOrderDate());
                    dto.setDeliveryDate(order.getDeliveryDate());

                    dto.setCustomerName(order.getCustomerName());
                    dto.setPhone(order.getPhone());
                    dto.setEmail(order.getEmail());
                    dto.setAddress(order.getAddress());
                    dto.setCity(order.getCity());
                    dto.setPaymentMethod(order.getPaymentMethod());

                    List<OrderItemDto> items = order.getOrderItems().stream()
                            .map(item -> {
                                OrderItemDto itemDto = new OrderItemDto();

                                itemDto.setOrderItemId(item.getOrderItemId());
                                itemDto.setQuantity(item.getQuantity());
                                itemDto.setPrice(item.getPrice());
                                itemDto.setProductId(item.getProduct().getProductId());
                                itemDto.setProductName(item.getProduct().getProductName());

                                List<OrderItemCustomizationDto> customizationDtos = item.getCustomizations()
                                        .stream()
                                        .map(custom -> {
                                            OrderItemCustomizationDto customDto = new OrderItemCustomizationDto();

                                            customDto.setOptionId(custom.getOption().getOptionId());
                                            customDto.setOptionName(custom.getOption().getOptionName());
                                            customDto.setValue(custom.getSelectedValue());
                                            customDto.setExtraPrice(custom.getExtraPrice().longValue());
                                            customDto.setOptionType(custom.getOption().getOptionType());
                                            customDto.setReferenceImage(custom.getReferenceImage());

                                            return customDto;
                                        }).toList();

                                itemDto.setCustomizations(customizationDtos);
                                return itemDto;
                            }).toList();
                    dto.setOrderItems(items);

                    return dto;
                }).toList();
    }

    @Override
    public OrderHistoryDto updateOrderStatus(Long orderId, String status){

        OrderEntity order = orderRepository.findById(orderId).orElseThrow(()->new RuntimeException("Order not found"));

        if(status.equals("CONFIRMED") && !"CONFIRMED".equals(order.getStatus()) && !order.getInventoryReduced()){
            reduceIngredients(order);
            order.setInventoryReduced(true);
        }

        if(!isValidTransition(order.getStatus(),status)){
            throw new RuntimeException("Invalid status transition.");
        }

        order.setStatus(status);

        OrderEntity updatedOrder = orderRepository.save(order);

        return orderMapper.toOrderHistoryDto(updatedOrder);
    }

    private boolean isValidTransition(String current, String next) {

        switch (current) {

            case "PENDING":
                return next.equals("PENDING") || next.equals("CONFIRMED") || next.equals("CANCELLED");

            case "CONFIRMED":
                return next.equals("CONFIRMED")
                        || next.equals("BAKING");

            case "BAKING":
                return next.equals("BAKING")
                        || next.equals("OUT FOR DELIVERY");

            case "OUT FOR DELIVERY":
                return next.equals("OUT FOR DELIVERY")
                        || next.equals("DELIVERED");

            case "DELIVERED":
                return next.equals("DELIVERED");

            case "CANCELLED":
                return next.equals("CANCELLED");

            default:
                return false;
        }

    }

    private void reduceIngredients(OrderEntity order){
        for(OrderItemEntity orderItem : order.getOrderItems()){
            List<RecipeEntity> recipes = recipeService.getRecipeEntities(orderItem.getProduct().getProductId());

            for(RecipeEntity recipe : recipes){
                double required = recipe.getQuantityRequired() * orderItem.getQuantity();

                inventoryConsumptionService.consumeItem(recipe.getInventory().getInventoryId(),required);
            }
        }
    }
}
