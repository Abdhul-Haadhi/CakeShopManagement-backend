package com.example.CakeShopManagement.service.Impl;

import com.example.CakeShopManagement.dto.*;
import com.example.CakeShopManagement.entity.*;
import com.example.CakeShopManagement.exceptions.AppException;
import com.example.CakeShopManagement.mappers.OrderMapper;
import com.example.CakeShopManagement.repository.*;
import com.example.CakeShopManagement.service.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
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
    private final NotificationService notificationService;

    public OrderServiceImpl(OrderRepository orderRepository, OrderMapper orderMapper, CartRepository cartRepository, OrderItemRepository orderItemRepository, OrderItemCustomizationRepository orderItemCustomizationRepository, PaymentRepository paymentRepository, PaymentService paymentService, CustomerRepository customerRepository, RecipeService recipeService, InventoryConsumptionService inventoryConsumptionService, NotificationService notificationService) {
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
        this.notificationService = notificationService;
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
                orderItem.setVariantType(cartItem.getVariantType());
                orderItem.setVariantValue(cartItem.getVariantValue());

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

            String message = "A new order has been placed. Tracking ID: " + savedOrder.getTrackingId();
            notificationService.notifyAdmin("New Order Received!", message, "ORDERS");

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

//    ---customer order history
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
                                itemDto.setVariantType(item.getVariantType());
                                itemDto.setVariantValue(item.getVariantValue());

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

//    --- admin order table
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
                                itemDto.setVariantType(item.getVariantType());
                                itemDto.setVariantValue(item.getVariantValue());

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
    public SalesReportDto getSalesReport(LocalDate startDate, LocalDate endDate){

        Date start = Date.from(startDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date end = Date.from(endDate.atTime(LocalTime.MAX).atZone(ZoneId.systemDefault()).toInstant());

        SalesReportDto dto = new SalesReportDto();

        dto.setTotalOrders(orderRepository.getTotalOrders(start, end));
        dto.setTotalRevenue(orderRepository.getTotalRevenue(start, end));
        dto.setTotalItemsSold(orderRepository.getTotalItemsSold(start, end));

        dto.setAverageOrderValue(dto.getTotalOrders()==0 ? 0 : dto.getTotalRevenue().doubleValue()/dto.getTotalOrders());

        dto.setMonthlySales(orderRepository.getMonthlySales(start, end));
        dto.setTopProducts(orderRepository.getTopProducts(start, end));
        System.out.println("Top Products = " + dto.getTopProducts().size());

        dto.getTopProducts().forEach(System.out::println);

        return dto;
    }


    @Override
    public OrderHistoryDto updateOrderStatus(Long orderId, String status){

        OrderEntity order = orderRepository.findById(orderId).orElseThrow(()->new RuntimeException("Order not found"));

        System.out.println("STATUS = " + status);
        System.out.println("Current = " + order.getStatus());

        if(status.equals("BAKING") && !"BAKING".equals(order.getStatus()) && !order.getInventoryReduced()){
            System.out.println("Reducing inventory...");
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

    public OrderHistoryDto cancelOrder(Long orderId){
        OrderEntity order = orderRepository.findById(orderId).orElseThrow(()->new RuntimeException("Order not found"));

        if("BAKING".equals(order.getStatus()) || "OUT FOR DELIVERY".equals(order.getStatus()) || "DELIVERED".equals(order.getStatus())){
            throw new RuntimeException("Order cannot be cancelled as preparation has already started.");
        }
        if ("CANCELLED".equals(order.getStatus())){
            throw new RuntimeException("Order is already cancelled.");
        }
        order.setStatus("CANCELLED");
        OrderEntity updatedOrder = orderRepository.save(order);

        String message = "Order #" + updatedOrder.getOrderId() + " (Tracking ID: " + updatedOrder.getTrackingId() + ") was cancelled by the customer.";
        notificationService.notifyAdmin("Order Cancelled", message, "ORDERS");

        return orderMapper.toOrderHistoryDto(updatedOrder);
    }

    private boolean isValidTransition(String current, String next) {

        switch (current) {

            case "PENDING":
                return next.equals("PENDING") || next.equals("CONFIRMED") || next.equals("CANCELLED");

            case "CONFIRMED":
                return next.equals("CONFIRMED") || next.equals("BAKING") || next.equals("CANCELLED");

            case "BAKING":
                return next.equals("BAKING") || next.equals("OUT FOR DELIVERY");

            case "OUT FOR DELIVERY":
                return next.equals("OUT FOR DELIVERY") || next.equals("DELIVERED");

            case "DELIVERED":
                return next.equals("DELIVERED");

            case "CANCELLED":
                return next.equals("CANCELLED");

            default:
                return false;
        }

    }

    private void reduceIngredients(OrderEntity order){

        class Consumption{
            Long inventoryId;
            Double quantity;

            Consumption(Long inventoryId, Double quantity){
                this.inventoryId = inventoryId;
                this.quantity = quantity;
            }
        }

        List<Consumption> consumptions = new java.util.ArrayList<>();

        // STEP 1 - Calculate all required quantities
        for(OrderItemEntity orderItem : order.getOrderItems()){

            List<RecipeEntity> recipes = recipeService.getRecipeEntities(
                    orderItem.getProduct().getProductId(),
                    orderItem.getVariantType(),
                    null        // <-- don't search by value
            );

            if(recipes.isEmpty()){
                throw new RuntimeException("Recipe not found for product: " + orderItem.getProduct().getProductName());
            }

            ProductVariant recipeVariant = recipes.get(0).getProductVariant();

            double factor;

            if(recipeVariant.getWeight() != null){

                factor = (double) orderItem.getVariantValue() / recipeVariant.getWeight();

            }else{

                factor = (double) orderItem.getVariantValue() / recipeVariant.getPieces();

            }

            for(RecipeEntity recipe : recipes){

                double required = recipe.getQuantityRequired() * factor * orderItem.getQuantity();

                consumptions.add(new Consumption(recipe.getInventory().getInventoryId(),required));

//                inventoryConsumptionService.consumeItem(recipe.getInventory().getInventoryId(),required);
            }
        }

        // STEP 2 - Validate ALL stock first
        for(Consumption c : consumptions){
            inventoryConsumptionService.validateStock(
                    c.inventoryId,
                    c.quantity
            );
        }

        // STEP 3 - Consume stock only if every validation passed
        for(Consumption c : consumptions){
            inventoryConsumptionService.consumeItem(
                    c.inventoryId,
                    c.quantity
            );
        }
    }
}
