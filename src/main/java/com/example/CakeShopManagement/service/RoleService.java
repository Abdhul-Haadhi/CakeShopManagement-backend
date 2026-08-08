package com.example.CakeShopManagement.service;

import com.example.CakeShopManagement.dto.RoleDto;
import com.example.CakeShopManagement.entity.RoleEntity;

import java.util.List;

public interface RoleService {
//    List<RoleEntity> getRoles();
    RoleDto addRole(RoleDto dto);

    List<RoleDto> getAllRoles();

    RoleDto updateRole(Long id, RoleDto dto);

    void deleteRole(Long id);
}
