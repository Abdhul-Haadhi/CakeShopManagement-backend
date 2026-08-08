package com.example.CakeShopManagement.controller;

import com.example.CakeShopManagement.dto.PermissionDto;
import com.example.CakeShopManagement.service.PermissionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class PermissionController {

    private final PermissionService permissionService;

    public PermissionController(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @PostMapping("/permissions")
    public PermissionDto addPermission(@RequestBody PermissionDto dto){
        return permissionService.addPermission(dto);
    }

    @GetMapping("/permissions")
    public List<PermissionDto> getAllPermissions(){
        return permissionService.getAllPermissions();
    }

    @PutMapping("/permissions/{id}")
    public PermissionDto updatePermission(@PathVariable Long id, @RequestBody PermissionDto dto){
        return permissionService.updatePermission(id,dto);
    }

    @DeleteMapping("/permissions/{id}")
    public void deletePermission(@PathVariable Long id){
        permissionService.deletePermission(id);
    }

}
