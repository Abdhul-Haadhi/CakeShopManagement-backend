package com.example.CakeShopManagement.service;

import com.example.CakeShopManagement.dto.PermissionDto;

import java.util.List;

public interface PermissionService {

    PermissionDto addPermission(PermissionDto dto);
    List<PermissionDto> getAllPermissions();

    PermissionDto updatePermission(Long id, PermissionDto dto);

    void deletePermission(Long id);


}
