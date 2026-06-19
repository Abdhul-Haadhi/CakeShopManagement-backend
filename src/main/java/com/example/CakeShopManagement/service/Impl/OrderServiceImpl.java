package com.example.CakeShopManagement.service.Impl;

import com.example.CakeShopManagement.dto.OrderHistoryDto;
import com.example.CakeShopManagement.dto.OrderItemCustomizationDto;
import com.example.CakeShopManagement.dto.OrderItemDto;
import com.example.CakeShopManagement.dto.PlaceOrderDto;
import com.example.CakeShopManagement.entity.*;
import com.example.CakeShopManagement.exceptions.AppException;
import com.example.CakeShopManagement.mappers.OrderMapper;
import com.example.CakeShopManagement.repository.*;
import com.example.CakeShopManagement.service.OrderService;
import com.example.CakeShopManagement.service.PaymentService;
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

    public OrderServiceImpl(OrderRepository orderRepository, OrderMapper orderMapper, CartRepository cartRepository, OrderItemRepository orderItemRepository, OrderItemCustomizationRepository orderItemCustomizationRepository, PaymentRepository paymentRepository, PaymentService paymentService) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
        this.cartRepository = cartRepository;
        this.orderItemRepository = orderItemRepository;
        this.orderItemCustomizationRepository = orderItemCustomizationRepository;
        this.paymentRepository = paymentRepository;
        this.paymentService = paymentService;
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
                orderItem.setPrice(cartItem.getProductEntity().getPrice() * cartItem.getQuantity());

                OrderItemEntity savedOrderItem = orderItemRepository.save(orderItem);

                for(CartItemCustomizationEntity cartCustom : cartItem.getCustomizations()){
                    OrderItemCustomizationEntity orderCustom  = new OrderItemCustomizationEntity();

                    orderCustom.setOrderItem(savedOrderItem);
                    orderCustom.setOption(cartCustom.getOption());
                    orderCustom.setSelectedValue(cartCustom.getSelectedValue());
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

        order.setStatus(status);

        OrderEntity updatedOrder = orderRepository.save(order);

        return orderMapper.toOrderHistoryDto(updatedOrder);
    }


}
