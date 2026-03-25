package com.example.CakeShopManagement.mappers;


import com.example.CakeShopManagement.dto.ProductsDto;
import com.example.CakeShopManagement.entity.ProductEntity;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel="spring", builder=@Builder(disableBuilder = true))
public interface ProductRegistrationMapper {
    ProductsDto toProductDto(ProductEntity productEntity);
    ProductEntity toProductEntity(ProductsDto productsDto);
    List<ProductsDto> toProductDtoList(List<ProductEntity> productEntityList);
}
