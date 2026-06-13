package com.example.CakeShopManagement.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ProductCustomizationDto {

    private Long optionId;

    private String optionName;

    private String optionType;

    private BigDecimal extraPrice;

    private List<String> optionValues;

    public ProductCustomizationDto() {
    }

    public ProductCustomizationDto(Long optionId, String optionName, String optionType, BigDecimal extraPrice, List<String> optionValues) {
        this.optionId = optionId;
        this.optionName = optionName;
        this.optionType = optionType;
        this.extraPrice = extraPrice;
        this.optionValues = optionValues;
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

    public List<String> getOptionValues() {
        return optionValues;
    }

    public void setOptionValues(List<String> optionValues) {
        this.optionValues = optionValues;
    }
}
