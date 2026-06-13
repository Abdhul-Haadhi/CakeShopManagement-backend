package com.example.CakeShopManagement.service.Impl;

import com.example.CakeShopManagement.dto.OrderHistoryDto;
import com.example.CakeShopManagement.dto.OrderItemDto;
import com.example.CakeShopManagement.dto.PlaceOrderDto;
import com.example.CakeShopManagement.entity.CartItemsEntity;
import com.example.CakeShopManagement.entity.OrderEntity;
import com.example.CakeShopManagement.entity.OrderItemEntity;
import com.example.CakeShopManagement.exceptions.AppException;
import com.example.CakeShopManagement.mappers.OrderMapper;
import com.example.CakeShopManagement.repository.CartRepository;
import com.example.CakeShopManagement.repository.OrderItemRepository;
import com.example.CakeShopManagement.repository.OrderRepository;
import com.example.CakeShopManagement.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final CartRepository cartRepository;
    private final OrderItemRepository orderItemRepository;

    public OrderServiceImpl(OrderRepository orderRepository, OrderMapper orderMapper, CartRepository cartRepository, OrderItemRepository orderItemRepository) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
        this.cartRepository = cartRepository;
        this.orderItemRepository = orderItemRepository;
    }

    @Override
    public PlaceOrderDto placeOrder(PlaceOrderDto placeOrderDto) {
        try {
            OrderEntity orderEntity = orderMapper.toOrderEntity(placeOrderDto);
            orderEntity.setOrderDate(new java.util.Date());
            orderEntity.setStatus("PENDING");
            orderEntity.setTrackingId(java.util.UUID.randomUUID().toString());

            OrderEntity savedOrder  = orderRepository.save(orderEntity);

            List<CartItemsEntity> cartItems = cartRepository.findAllById(placeOrderDto.getCartItemIds());

            for(CartItemsEntity cartItem:cartItems){

                OrderItemEntity orderItem = new OrderItemEntity();

                orderItem.setOrder(savedOrder);
                orderItem.setProduct(cartItem.getProductEntity());
                orderItem.setQuantity(cartItem.getQuantity());
                orderItem.setPrice(cartItem.getProductEntity().getPrice() * cartItem.getQuantity());

                orderItemRepository.save(orderItem);
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
                                itemDto.setPrice(itemDto.getPrice());
                                itemDto.setProductId(item.getProduct().getProductId());
                                itemDto.setProductName(item.getProduct().getProductName());

                                return itemDto;
                            }).toList();
                    dto.setOrderItems(items);
                    return dto;
                }).toList();
    }

}
