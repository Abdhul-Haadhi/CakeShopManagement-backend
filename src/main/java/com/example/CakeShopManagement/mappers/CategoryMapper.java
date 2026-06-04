package com.example.CakeShopManagement.mappers;

import com.example.CakeShopManagement.dto.CategoryDto;
import com.example.CakeShopManagement.entity.CategoryEntity;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel="spring", builder=@Builder(disableBuilder = true))
public interface CategoryMapper {
    CategoryDto toCategoryDto(CategoryEntity categoryEntity);
    CategoryEntity toCategoryEntity(CategoryDto categoryDto);
    List<CategoryDto> toCategoryDtoList(List<CategoryEntity> categoryEntities);
}
