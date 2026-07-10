package com.example.CakeShopManagement.mappers;

import com.example.CakeShopManagement.dto.InventoryDto;
import com.example.CakeShopManagement.entity.InventoryEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface InventoryMapper {
    InventoryDto toDto(InventoryEntity entity);
    InventoryEntity toEntity(InventoryDto dto);
}
