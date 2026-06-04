package com.example.CakeShopManagement.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "customization_option_values")
public class CustomizationOptionValueEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long valueId;

    @ManyToOne
    @JoinColumn(name = "option_id")
    private CustomizationOptionEntity option;

    private String value;


    public CustomizationOptionValueEntity() {
    }

    public CustomizationOptionValueEntity(Long valueId, CustomizationOptionEntity option, String value) {
        this.valueId = valueId;
        this.option = option;
        this.value = value;
    }

    public Long getValueId() {
        return valueId;
    }

    public void setValueId(Long valueId) {
        this.valueId = valueId;
    }

    public CustomizationOptionEntity getOption() {
        return option;
    }

    public void setOption(CustomizationOptionEntity option) {
        this.option = option;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
