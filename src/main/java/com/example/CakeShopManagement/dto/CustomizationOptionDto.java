package com.example.CakeShopManagement.dto;

import lombok.Data;

@Data
public class CustomizationOptionDto {

    private Long optionId;
    private String optionName;
    private String optionType;

    public CustomizationOptionDto() {
    }

    public CustomizationOptionDto(Long optionId, String optionName, String optionType) {
        this.optionId = optionId;
        this.optionName = optionName;
        this.optionType = optionType;
    }

    public Long getOptionId() {
        return optionId;
    }

    public void setOptionId(Long optionId) {
        this.optionId = optionId;
    }

    public String getOptionName() {
        return optionName;
    }

    public void setOptionName(String optionName) {
        this.optionName = optionName;
    }

    public String getOptionType() {
        return optionType;
    }

    public void setOptionType(String optionType) {
        this.optionType = optionType;
    }
}
