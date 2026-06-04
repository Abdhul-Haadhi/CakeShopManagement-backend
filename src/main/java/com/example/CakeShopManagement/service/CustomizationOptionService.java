package com.example.CakeShopManagement.service;

import com.example.CakeShopManagement.dto.CustomizationOptionDto;
import com.example.CakeShopManagement.entity.CustomizationOptionEntity;

import java.util.List;

public interface CustomizationOptionService {
    CustomizationOptionDto addOption(CustomizationOptionDto dto);
    List<CustomizationOptionDto> getAllOptions();
    CustomizationOptionDto updateOptions(Long optionId, CustomizationOptionDto dto);
//    boolean deleteOption(Long optionId);
    void deleteOption(Long optionId);
}
