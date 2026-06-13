package com.example.CakeShopManagement.entity;

import com.example.CakeShopManagement.dto.CustomizationOptionDto;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "customization_options")
public class CustomizationOptionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long optionId;
    private String optionName;
    private String optionType;

    @OneToMany(
            mappedBy = "option",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<CustomizationOptionValueEntity> values = new ArrayList<>();


//    public CustomizationOptionDto getDto(){
//        return new CustomizationOptionDto(optionId, optionName, optionType);
//    }

    public CustomizationOptionDto getDto(){
        CustomizationOptionDto dto = new CustomizationOptionDto();

        dto.setOptionId(optionId);
        dto.setOptionName(optionName);
        dto.setOptionType(optionType);

        dto.setOptionValue(
                values.stream().map(CustomizationOptionValueEntity::getValue).toList()
        );
        return dto;
    }

    public CustomizationOptionEntity() {
    }

    public CustomizationOptionEntity(Long optionId, String optionName, String optionType, List<CustomizationOptionValueEntity> values) {
        this.optionId = optionId;
        this.optionName = optionName;
        this.optionType = optionType;
        this.values = values;
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

    public List<CustomizationOptionValueEntity> getValues() {
        return values;
    }

    public void setValues(List<CustomizationOptionValueEntity> values) {
        this.values = values;
    }
}
