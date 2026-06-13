package com.example.CakeShopManagement.dto;

import lombok.Data;

import java.util.List;

@Data
public class CustomizationOptionDto {

    private Long optionId;
    private String optionName;
    private String optionType;
    private List<String> optionValue;

    public CustomizationOptionDto() {
    }

    public CustomizationOptionDto(Long optionId, String optionName, String optionType, List<String> optionValue) {
        this.optionId = optionId;
        this.optionName = optionName;
        this.optionType = optionType;
        this.optionValue = optionValue;
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

    public List<String> getOptionValue() {
        return optionValue;
    }

    public void setOptionValue(List<String> optionValue) {
        this.optionValue = optionValue;
    }
}
