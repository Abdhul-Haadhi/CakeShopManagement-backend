package com.example.CakeShopManagement.service.Impl;

import com.example.CakeShopManagement.dto.RoleDto;
import com.example.CakeShopManagement.entity.PermissionEntity;
import com.example.CakeShopManagement.entity.RoleEntity;
import com.example.CakeShopManagement.repository.PermissionRepository;
import com.example.CakeShopManagement.repository.RoleRepository;
import com.example.CakeShopManagement.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;


    public RoleServiceImpl(RoleRepository roleRepository, PermissionRepository permissionRepository) {
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
    }

    @Override
    public RoleDto addRole(RoleDto dto) {

        RoleEntity role = new RoleEntity();

        role.setRoleName(dto.getRoleName());
        role.setDescription(dto.getDescription());

        if(dto.getPermissionIds()!=null){

            Set<PermissionEntity> permissions =
                    dto.getPermissionIds()
                            .stream()
                            .map(id->permissionRepository.findById(id).orElseThrow())
                            .collect(Collectors.toSet());

            role.setPermissions(permissions);

        }

        return map(roleRepository.save(role));

    }

    @Override
    public List<RoleDto> getAllRoles() {

        List<RoleEntity> roles = roleRepository.findAll();

        List<RoleDto> result = new ArrayList<>();

        for (RoleEntity role : roles) {
            result.add(map(role));
        }

        return result;

    }

    @Override
    public RoleDto updateRole(Long id, RoleDto dto) {

        RoleEntity role = roleRepository.findById(id).orElseThrow();

        role.setRoleName(dto.getRoleName());
        role.setDescription(dto.getDescription());

        Set<PermissionEntity> permissions = dto.getPermissionIds()
                        .stream()
                        .map(pid->permissionRepository.findById(pid).orElseThrow())
                        .collect(Collectors.toSet());

        role.setPermissions(permissions);

        return map(roleRepository.save(role));

    }

    @Override
    public void deleteRole(Long id) {

        roleRepository.deleteById(id);

    }

    private RoleDto map(RoleEntity role) {

        RoleDto dto = new RoleDto();

        dto.setRoleId(role.getRoleId());
        dto.setRoleName(role.getRoleName());
        dto.setDescription(role.getDescription());

        dto.setPermissionIds(
                role.getPermissions()
                        .stream()
                        .map(PermissionEntity::getPermissionId)
                        .collect(Collectors.toList())
        );

        return dto;
    }
}
