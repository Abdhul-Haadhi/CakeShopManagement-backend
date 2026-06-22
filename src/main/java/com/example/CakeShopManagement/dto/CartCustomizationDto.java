package com.example.CakeShopManagement.dto;

import lombok.Data;

@Data
public class CartCustomizationDto {

    private Long optionId;
    private String optionName;
    private Object value;
    private Long extraPrice;
    private byte[] referenceImage;

    public CartCustomizationDto() {
    }

    public CartCustomizationDto(Long optionId, String optionName, Object value, Long extraPrice, byte[] referenceImage) {
        this.optionId = optionId;
        this.optionName = optionName;
        this.value = value;
        this.extraPrice = extraPrice;
        this.referenceImage = referenceImage;
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

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public Long getExtraPrice() {
        return extraPrice;
    }

    public void setExtraPrice(Long extraPrice) {
        this.extraPrice = extraPrice;
    }

    public byte[] getReferenceImage() {
        return referenceImage;
    }

    public void setReferenceImage(byte[] referenceImage) {
        this.referenceImage = referenceImage;
    }
}
