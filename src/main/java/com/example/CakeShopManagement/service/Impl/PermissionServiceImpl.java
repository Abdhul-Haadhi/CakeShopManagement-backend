package com.example.CakeShopManagement.service.Impl;

import com.example.CakeShopManagement.dto.PermissionDto;
import com.example.CakeShopManagement.entity.PermissionEntity;
import com.example.CakeShopManagement.entity.RoleEntity;
import com.example.CakeShopManagement.repository.PermissionRepository;
import com.example.CakeShopManagement.service.PermissionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PermissionServiceImpl implements PermissionService {

    private final PermissionRepository permissionRepository;

    public PermissionServiceImpl(PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    @Override
    public PermissionDto addPermission(PermissionDto dto) {

        PermissionEntity permission = new PermissionEntity();

        permission.setPermissionName(dto.getPermissionName());
        permission.setDescription(dto.getDescription());

        PermissionEntity saved = permissionRepository.save(permission);

        return map(saved);
    }

    @Override
    public List<PermissionDto> getAllPermissions() {

        return permissionRepository.findAll()
                .stream()
                .map(this::map)
                .toList();
    }

    @Override
    public PermissionDto updatePermission(Long id, PermissionDto dto) {

        PermissionEntity permission = permissionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Permission not found"));

        permission.setPermissionName(dto.getPermissionName());
        permission.setDescription(dto.getDescription());

        return map(permissionRepository.save(permission));
    }

//    @Override
//    public void deletePermission(Long id) {
//
//        permissionRepository.deleteById(id);
//
//    }

    @Override
    @Transactional
    public void deletePermission(Long id) {
        PermissionEntity permission = permissionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Permission not found"));

        // Safely remove this permission from any roles that currently own it
        // This prevents the Foreign Key Constraint database error
        for (RoleEntity role : permission.getRoles()) {
            role.getPermissions().remove(permission);
        }

        permissionRepository.delete(permission);
    }

    private PermissionDto map(PermissionEntity entity){
        PermissionDto dto = new PermissionDto();
        dto.setPermissionId(entity.getPermissionId());
        dto.setPermissionName(entity.getPermissionName());
        dto.setDescription(entity.getDescription());
        return dto;
    }

}
