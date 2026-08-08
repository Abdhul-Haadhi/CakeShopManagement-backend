package com.example.CakeShopManagement.service.Impl;

import com.example.CakeShopManagement.dto.*;
import com.example.CakeShopManagement.entity.*;
import com.example.CakeShopManagement.exceptions.AppException;
import com.example.CakeShopManagement.mappers.OrderMapper;
import com.example.CakeShopManagement.repository.*;
import com.example.CakeShopManagement.service.*;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Base64;
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
    private final ProductRegistrationRepository productRepository;
    private final ProductVariantRepository productVariantRepository;
    private final ProductCustomizationRepository productCustomizationRepository;

    public OrderServiceImpl(OrderRepository orderRepository, OrderMapper orderMapper, CartRepository cartRepository, OrderItemRepository orderItemRepository, OrderItemCustomizationRepository orderItemCustomizationRepository, PaymentRepository paymentRepository, PaymentService paymentService, CustomerRepository customerRepository, RecipeService recipeService, InventoryConsumptionService inventoryConsumptionService, NotificationService notificationService, ProductRegistrationRepository productRepository, ProductVariantRepository productVariantRepository, ProductCustomizationRepository productCustomizationRepository) {
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
        this.productRepository = productRepository;
        this.productVariantRepository = productVariantRepository;
        this.productCustomizationRepository = productCustomizationRepository;
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


    @Override
    @Transactional
    public WalkInOrderDto createWalkInOrder(WalkInOrderDto dto){
        try {
            // 1. Basic validation
            if(dto.getCustomerName() == null || dto.getCustomerName().trim().isEmpty()){
                throw new RuntimeException("Customer name is required.");
            }
            if(dto.getPhone() == null || dto.getPhone().trim().isEmpty()){
                throw new RuntimeException("Customer phone number is required.");
            }
            if (dto.getItems() == null || dto.getItems().isEmpty()){
                throw new RuntimeException("At least one product is required.");
            }
            if (dto.getPaymentMethod() == null || dto.getPaymentMethod().trim().isEmpty()){
                throw new RuntimeException("Payment method is required.");
            }

            // 2. Create OrderEntity
            OrderEntity order = new OrderEntity();
            order.setCustomerName(dto.getCustomerName().trim());
            order.setPhone(dto.getPhone().trim());
            order.setDeliveryDate(java.sql.Date.valueOf(dto.getDeliveryDate()));
            order.setCustomer(null);
            order.setSessionId(null);
            order.setOrderType("WALK_IN");
            order.setNotes(dto.getNotes());
            order.setOrderDate(new java.util.Date());
            order.setStatus("PENDING");
            order.setInventoryReduced(false);
            order.setTrackingId(java.util.UUID.randomUUID().toString());
            order.setPaymentMethod(dto.getPaymentMethod().toUpperCase());


            // 3. Payment validation
            PaymentEntity existingPayment = null;
            if ("CARD".equalsIgnoreCase(dto.getPaymentMethod())){
                if (dto.getPaymentId() == null){
                    throw new RuntimeException("Card payment is required.");
                }
                existingPayment = paymentRepository.findById(dto.getPaymentId()).orElseThrow(()->new RuntimeException("Payment not found"));
            }

            // 4. Calculate order totals from database
            long calculatedTotal = 0;
            long calculatedQuantity = 0;

            // 5. Create Order Items
            List<OrderItemEntity> orderItems = new ArrayList<>();
            for (WalkInOrderItemDto itemDto : dto.getItems()){
                if(itemDto.getProductId() == null){
                    throw new RuntimeException("Product ID is required.");
                }
                if (itemDto.getQuantity() == null || itemDto.getQuantity() <= 0){
                    throw new RuntimeException("Invalid product quantity.");
                }

                // Find product
                ProductEntity product = productRepository.findById(itemDto.getProductId()).orElseThrow(()->new RuntimeException("Product not found"+ itemDto.getProductId()));

                if(Boolean.FALSE.equals(product.getActive())){
                    throw new RuntimeException("Product is currently unavailable: " + product.getProductName());
                }

                // Find selected variant
                ProductVariant variant = null;

                if (itemDto.getVariantId() != null) {

                    variant = productVariantRepository.findByVariantIdAndProductProductId(
                                    itemDto.getVariantId(),
                                    itemDto.getProductId()
                            )
                            .orElseThrow(() ->new RuntimeException("Selected variant does not belong to product: "+ product.getProductName()));

                    if (Boolean.FALSE.equals(variant.getAvailable())) {

                        throw new RuntimeException("Selected variant is unavailable for product: "+ product.getProductName());
                    }
                }

                // Determine base price
                double basePrice = 0;
                if (variant != null){
                    if (variant.getPrice() == null){
                        throw new RuntimeException("Variant price is not configured.");
                    }
                    basePrice = variant.getPrice();
                }
                else {
                    throw new RuntimeException("A product variant must be selected for: " + product.getProductName());
                }

                // Create OrderItem
                OrderItemEntity orderItem = new OrderItemEntity();

                orderItem.setOrder(order);
                orderItem.setProduct(product);

                orderItem.setQuantity(itemDto.getQuantity());

                // Variant information
                orderItem.setVariantType(variant.getVariantType() != null ? variant.getVariantType().name() : null);

                if (variant.getVariantType() != null){
                    switch (variant.getVariantType().name()){
                        case "WEIGHT":
                            orderItem.setVariantValue(variant.getWeight());
                            break;

                        case "PIECE":
                            orderItem.setVariantValue(variant.getPieces());
                            break;

                        default:
                            orderItem.setVariantValue(null);
                    }
                }

                // Customizations
                double customizationTotal = 0;

                List<OrderItemCustomizationEntity> customizationEntities = new ArrayList<>();

                if (itemDto.getCustomizations() != null){
                    for (WalkInCustomizationDto customDto : itemDto.getCustomizations()){
                        if (customDto.getOptionId() == null) {
                            throw new RuntimeException("Customization option ID is required.");
                        }

                        ProductCustomizationEntity productCustomization = productCustomizationRepository.findByProductProductIdAndCustomizationOptionOptionId(
                                product.getProductId(),
                                customDto.getOptionId()
                        ).orElseThrow(()->new RuntimeException("Customization option is not available for product: " + product.getProductName()));

                        CustomizationOptionEntity option = productCustomization.getCustomizationOption();

                        // Use database price
                        BigDecimal extraPrice =  productCustomization.getExtraPrice();
                        if (extraPrice == null) {
                            extraPrice = BigDecimal.ZERO;
                        }

                        customizationTotal += extraPrice.doubleValue();

                        // Create order customization
                        OrderItemCustomizationEntity orderCustomization = new OrderItemCustomizationEntity();

                        orderCustomization.setOrderItem(orderItem);

                        orderCustomization.setOption(option);

                        orderCustomization.setSelectedValue(customDto.getValue());

                        orderCustomization.setExtraPrice(extraPrice);

                        // Reference image

                        if (customDto.getReferenceImage() != null && !customDto.getReferenceImage().isEmpty()){
                            try {
                                byte[] imageBytes = Base64.getDecoder().decode(customDto.getReferenceImage());

                                orderCustomization.setReferenceImage(imageBytes);
                            }
                            catch (IllegalArgumentException e) {
                                throw new RuntimeException("Invalid customization reference image.");
                            }
                        }
                        customizationEntities.add(orderCustomization);
                    }
                }

                // Final item price
                double finalUnitPrice = basePrice + customizationTotal;

                long unitPrice = Math.round(finalUnitPrice);

                long subtotal = unitPrice * itemDto.getQuantity();

                orderItem.setPrice(unitPrice);

                orderItem.setCustomizations(customizationEntities);

                orderItems.add(orderItem);

                calculatedTotal += subtotal;

                calculatedQuantity += itemDto.getQuantity();
            }

            // 6. Set order totals
            order.setTotalAmount(calculatedTotal);
            order.setQuantity(calculatedQuantity);
            order.setOrderItems(orderItems);

            // 7. Save order
            OrderEntity savedOrder = orderRepository.save(order);

            // 8. Process payment
            if (existingPayment != null) {

                existingPayment.setOrder(savedOrder);
                existingPayment.setPaymentMethod("CARD");
                existingPayment.setPaymentStatus("PAID");
                existingPayment.setAmount((double) calculatedTotal);

                paymentRepository.save(existingPayment);

            }
            else {
                PaymentEntity payment = new PaymentEntity();

                payment.setOrder(savedOrder);
                payment.setPaymentMethod(dto.getPaymentMethod().toUpperCase());
                if ("CASH".equalsIgnoreCase(dto.getPaymentMethod())) {

                    payment.setPaymentStatus("PAID");
                    payment.setGatewayName("Cash");

                }
                else {
                    payment.setPaymentStatus("PENDING");
                }

                payment.setAmount((double) calculatedTotal);

                payment.setTransactionId(null);

                payment.setPaymentDate(LocalDate.now());

                paymentRepository.save(payment);
            }

            // 9. Notification
            String message =
                    "A new walk-in order has been placed. " +
                            "Order ID: " + savedOrder.getOrderId() +
                            ", Customer: " + savedOrder.getCustomerName() +
                            ", Tracking ID: " + savedOrder.getTrackingId();


            notificationService.notifyAdmin(
                    "New Walk-in Order",
                    message,
                    "ORDERS"
            );

            // 10. Return order
            WalkInOrderDto response = new WalkInOrderDto();

            response.setCustomerName(savedOrder.getCustomerName());

            response.setPhone(savedOrder.getPhone());

            response.setOrderType(savedOrder.getOrderType());

            response.setPaymentMethod(savedOrder.getPaymentMethod());

            response.setPaymentId(savedOrder.getPayment() != null ? savedOrder.getPayment().getPaymentId() : null);

            response.setNotes(savedOrder.getNotes());

            response.setTotalAmount(savedOrder.getTotalAmount());

            response.setQuantity(savedOrder.getQuantity());

            return response;

        }
        catch (Exception e){
            throw new AppException("Walk-in order creation failed: " + e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
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
