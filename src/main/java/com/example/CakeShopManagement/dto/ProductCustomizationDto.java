package com.example.CakeShopManagement.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductCustomizationDto {

    private Long optionId;

    private String optionName;

    private String optionType;

    private BigDecimal extraPrice;

    public ProductCustomizationDto() {
    }

    public ProductCustomizationDto(Long optionId, String optionName, String optionType, BigDecimal extraPrice) {
        this.optionId = optionId;
        this.optionName = optionName;
        this.optionType = optionType;
        this.extraPrice = extraPrice;
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

    public BigDecimal getExtraPrice() {
        return extraPrice;
    }

    public void setExtraPrice(BigDecimal extraPrice) {
        this.extraPrice = extraPrice;
    }
}
