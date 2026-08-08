package com.example.CakeShopManagement.mappers;


import com.example.CakeShopManagement.dto.EmployeeDto;
import com.example.CakeShopManagement.entity.EmployeeEntity;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel="spring", builder=@Builder(disableBuilder = true))
public interface EmployeeMapper {
    @Mapping(source = "role.roleId", target = "roleId")
    @Mapping(source = "role.roleName", target = "roleName")
    EmployeeDto toEmployeeDto(EmployeeEntity employeeEntity);

    @Mapping(target = "role", ignore = true)
    EmployeeEntity toEmployeeEntity(EmployeeDto employeeDto);

    List<EmployeeDto> toEmployeeDtoList(List<EmployeeEntity> employeeEntityList);
}
