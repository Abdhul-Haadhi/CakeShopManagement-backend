package com.example.CakeShopManagement.dto;

import lombok.Data;

@Data
public class OrderItemCustomizationDto {
    private Long optionId;
    private String optionName;
    private String value;
    private Long extraPrice;

    public OrderItemCustomizationDto() {
    }

    public OrderItemCustomizationDto(Long optionId, String optionName, String value, Long extraPrice) {
        this.optionId = optionId;
        this.optionName = optionName;
        this.value = value;
        this.extraPrice = extraPrice;
    }

    public String getOptionName() {
        return optionName;
    }

    public void setOptionName(String optionName) {
        this.optionName = optionName;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Long getExtraPrice() {
        return extraPrice;
    }

    public void setExtraPrice(Long extraPrice) {
        this.extraPrice = extraPrice;
    }

    public Long getOptionId() {
        return optionId;
    }

    public void setOptionId(Long optionId) {
        this.optionId = optionId;
    }
}
