package com.example.CakeShopManagement.controller;


import com.example.CakeShopManagement.dto.RoleDto;
import com.example.CakeShopManagement.service.RoleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping("/roles")
    public RoleDto addRole(@RequestBody RoleDto dto){
        return roleService.addRole(dto);
    }

    @GetMapping("/roles")
    public List<RoleDto> getAllRoles(){
        System.out.println("GET ROLES CALLED");
        List<RoleDto> roles = roleService.getAllRoles();
        System.out.println(roles.size());
        return roles;
    }

    @PutMapping("/roles/{id}")
    public RoleDto updateRole(@PathVariable Long id, @RequestBody RoleDto dto){
        return roleService.updateRole(id,dto);
    }

    @DeleteMapping("/roles/{id}")
    public void deleteRole(@PathVariable Long id){
        roleService.deleteRole(id);
    }
}
