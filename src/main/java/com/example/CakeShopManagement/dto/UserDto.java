package com.example.CakeShopManagement.dto;

import com.example.CakeShopManagement.enums.UserRole;
import lombok.Data;

@Data
public class UserDto {
    private Long userId;
    private String username;
    private String password;
    private String email;
    private int contactNumber;
    private String address;
//    private UserRole userRole;
    private Long roleId;
    private String roleName;

    public UserDto() {
    }

    public UserDto(Long userId, String username, String password, String email, int contactNumber, String address, Long roleId, String roleName) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.email = email;
        this.contactNumber = contactNumber;
        this.address = address;
        this.roleId = roleId;
        this.roleName = roleName;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(int contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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
}


