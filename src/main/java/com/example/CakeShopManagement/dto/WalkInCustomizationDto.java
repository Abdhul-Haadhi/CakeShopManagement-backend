package com.example.CakeShopManagement.dto;

public class WalkInCustomizationDto {
    private Long optionId;
    private String optionName;
    private String value;
    private Long extraPrice;
    private String optionType;
    private String referenceImage;

    public WalkInCustomizationDto() {
    }

    public WalkInCustomizationDto(Long optionId, String optionName, String value, Long extraPrice, String optionType, String referenceImage) {
        this.optionId = optionId;
        this.optionName = optionName;
        this.value = value;
        this.extraPrice = extraPrice;
        this.optionType = optionType;
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

    public String getOptionType() {
        return optionType;
    }

    public void setOptionType(String optionType) {
        this.optionType = optionType;
    }

    public String getReferenceImage() {
        return referenceImage;
    }

    public void setReferenceImage(String referenceImage) {
        this.referenceImage = referenceImage;
    }
}
