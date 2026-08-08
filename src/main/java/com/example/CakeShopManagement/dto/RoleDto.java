package com.example.CakeShopManagement.dto;


import java.util.List;

public class RoleDto {
    private Long roleId;
    private String roleName;
    private String description;
    private List<Long> permissionIds;

    public RoleDto() {
    }

    public RoleDto(Long roleId, String roleName, String description, List<Long> permissionIds) {
        this.roleId = roleId;
        this.roleName = roleName;
        this.description = description;
        this.permissionIds = permissionIds;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Long> getPermissionIds() {
        return permissionIds;
    }

    public void setPermissionIds(List<Long> permissionIds) {
        this.permissionIds = permissionIds;
    }
}
