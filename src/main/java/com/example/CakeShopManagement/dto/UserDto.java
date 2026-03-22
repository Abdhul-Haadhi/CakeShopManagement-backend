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
    private UserRole userRole;

    public UserDto() {
    }

    public UserDto(Long userId, String username, String password, String email, int contactNumber, String address, UserRole userRole) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.email = email;
        this.contactNumber = contactNumber;
        this.address = address;
        this.userRole = userRole;
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

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }
}


