package com.example.CakeShopManagement.service.Impl;

import com.example.CakeShopManagement.dto.CustomizationOptionValueDto;
import com.example.CakeShopManagement.entity.CustomizationOptionEntity;
import com.example.CakeShopManagement.entity.CustomizationOptionValueEntity;
import com.example.CakeShopManagement.repository.CustomizationOptionRepository;
import com.example.CakeShopManagement.repository.CustomizationOptionValueRepository;
import com.example.CakeShopManagement.service.CustomizationOptionValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomizationOptionValueServiceImpl implements CustomizationOptionValueService {
    @Autowired
    private final CustomizationOptionValueRepository customizationOptionValueRepository;

    @Autowired
    private final CustomizationOptionRepository customizationOptionRepository;

    public CustomizationOptionValueServiceImpl(CustomizationOptionValueRepository customizationOptionValueRepository, CustomizationOptionRepository customizationOptionRepository, CustomizationOptionValueRepository customizationOptionValueRepository1, CustomizationOptionRepository customizationOptionRepository1) {
        this.customizationOptionValueRepository = customizationOptionValueRepository1;
        this.customizationOptionRepository = customizationOptionRepository1;
    }


    @Override
    public CustomizationOptionValueDto addValue(CustomizationOptionValueDto dto){
        CustomizationOptionEntity option = customizationOptionRepository.findById(dto.getOptionId()).orElseThrow();

        CustomizationOptionValueEntity value = new CustomizationOptionValueEntity();

        value.setOption(option);
        value.setValue(dto.getValue());

        customizationOptionValueRepository.save(value);

        return new CustomizationOptionValueDto(
                value.getValueId(),
                option.getOptionId(),
                value.getValue()
        );

    }


    @Override
    public List<CustomizationOptionValueDto> getValuesByOption(Long optionId){
        return customizationOptionValueRepository
                .findByOptionOptionId(optionId)
                .stream()
                .map(value ->
                        new CustomizationOptionValueDto(
                                value.getValueId(),
                                value.getOption().getOptionId(),
                                value.getValue()
                        ))
                .toList();
    }

    @Override
    public CustomizationOptionValueDto updateValue(Long valueId, CustomizationOptionValueDto dto){
        CustomizationOptionValueEntity value = customizationOptionValueRepository.findById(valueId).orElseThrow();

        value.setValue(dto.getValue());

        customizationOptionValueRepository.save(value);

        return new CustomizationOptionValueDto(
                value.getValueId(),
                value.getOption().getOptionId(),
                value.getValue()
        );
    }


    @Override
    public void deleteValue(Long valueId){
        customizationOptionValueRepository.deleteById(valueId);
    }


}
