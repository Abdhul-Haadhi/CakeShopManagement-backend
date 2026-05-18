package com.example.CakeShopManagement.mappers;

import com.example.CakeShopManagement.dto.PlaceOrderDto;
import com.example.CakeShopManagement.entity.OrderEntity;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;

@Mapper(componentModel="spring", builder=@Builder(disableBuilder = true))
public interface OrderMapper {
    PlaceOrderDto toPlaceOrderDto(OrderEntity orderEntity);
    OrderEntity toOrderEntity(PlaceOrderDto placeOrderDto);
}
