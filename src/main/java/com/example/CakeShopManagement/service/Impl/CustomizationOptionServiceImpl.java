package com.example.CakeShopManagement.service.Impl;

import com.example.CakeShopManagement.dto.CustomizationOptionDto;
import com.example.CakeShopManagement.entity.CustomizationOptionEntity;
import com.example.CakeShopManagement.repository.CustomizationOptionRepository;
import com.example.CakeShopManagement.service.CustomizationOptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomizationOptionServiceImpl implements CustomizationOptionService {

    @Autowired
    private final CustomizationOptionRepository customizationOptionRepository;

    public CustomizationOptionServiceImpl(CustomizationOptionRepository customizationOptionRepository) {
        this.customizationOptionRepository = customizationOptionRepository;
    }

    @Override
    public CustomizationOptionDto addOption(CustomizationOptionDto dto){

        CustomizationOptionEntity option = new CustomizationOptionEntity();

        option.setOptionName(dto.getOptionName());
        option.setOptionType(dto.getOptionType());

        CustomizationOptionEntity saved = customizationOptionRepository.save(option);

        return saved.getDto();
    }

    @Override
    public List<CustomizationOptionDto> getAllOptions(){
        return customizationOptionRepository.findAll()
                .stream()
                .map(CustomizationOptionEntity::getDto)
                .toList();
    }

    @Override
    public CustomizationOptionDto updateOptions(Long optionId, CustomizationOptionDto dto){

        CustomizationOptionEntity option = customizationOptionRepository.findById(optionId)
                .orElseThrow(
                        ()->new RuntimeException("Customization Option Not Found")
                );

        option.setOptionName(dto.getOptionName());
        option.setOptionType(dto.getOptionType());

        CustomizationOptionEntity updated = customizationOptionRepository.save(option);

        return updated.getDto();
    }

//    @Override
//    public boolean deleteOption(Long optionId){
//        if(!customizationOptionRepository.existsById(optionId)){
//            return false;
//        }
//        customizationOptionRepository.deleteById(optionId);
//        return false;
//    }

    @Override
    public void deleteOption(Long optionId){
        CustomizationOptionEntity option = customizationOptionRepository.findById(optionId).orElseThrow(()->new RuntimeException("Customization Option Not Found"));
        customizationOptionRepository.delete(option);
    }

}
