package com.example.CakeShopManagement.mappers;

import com.example.CakeShopManagement.dto.CustomerDto;
import com.example.CakeShopManagement.entity.CustomerEntity;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel="spring", builder=@Builder(disableBuilder = true))
public interface CustomerMapper {

    CustomerDto toCustomerDto(CustomerEntity customerEntity);
    CustomerEntity toCustomerEntity(CustomerDto customerDto);
    List<CustomerDto> toCustomerDtoList(List<CustomerEntity> customerEntityList);

}
