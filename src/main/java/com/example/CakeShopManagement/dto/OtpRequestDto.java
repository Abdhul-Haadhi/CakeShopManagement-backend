package com.example.CakeShopManagement.dto;

import lombok.Data;

@Data
public class OtpRequestDto {
    private String phone;

    public OtpRequestDto() {
    }

    public OtpRequestDto(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
