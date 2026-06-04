package com.example.CakeShopManagement.service;

import com.example.CakeShopManagement.dto.CustomizationOptionValueDto;

import java.util.List;

public interface CustomizationOptionValueService {

    CustomizationOptionValueDto addValue(CustomizationOptionValueDto dto);
    List<CustomizationOptionValueDto> getValuesByOption(Long optionId);
    CustomizationOptionValueDto updateValue(Long valueId, CustomizationOptionValueDto dto);
    void deleteValue(Long valueId);
}
