package com.example.CakeShopManagement.dto;


import lombok.Data;

@Data
public class CustomizationOptionValueDto {

    private Long valueId;
    private Long optionId;
    private String value;

    public CustomizationOptionValueDto() {
    }

    public CustomizationOptionValueDto(Long valueId, Long optionId, String value) {
        this.valueId = valueId;
        this.optionId = optionId;
        this.value = value;
    }

    public Long getValueId() {
        return valueId;
    }

    public void setValueId(Long valueId) {
        this.valueId = valueId;
    }

    public Long getOptionId() {
        return optionId;
    }

    public void setOptionId(Long optionId) {
        this.optionId = optionId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
