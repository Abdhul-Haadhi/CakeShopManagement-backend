package com.example.CakeShopManagement.controller;

import com.example.CakeShopManagement.dto.CustomizationOptionValueDto;
import com.example.CakeShopManagement.service.CustomizationOptionValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employee")
public class CustomizationOptionValueController {

    @Autowired
    private final CustomizationOptionValueService valueService;


    public CustomizationOptionValueController(CustomizationOptionValueService valueService) {
        this.valueService = valueService;
    }

    @PostMapping("/customization-values")
    public CustomizationOptionValueDto addValue(@RequestBody CustomizationOptionValueDto dto) {
        return valueService.addValue(dto);
    }


    @GetMapping("/customization-values/{optionId}")
    public List<CustomizationOptionValueDto> getValues(@PathVariable long optionId) {
        return valueService.getValuesByOption(optionId);
    }

    @PutMapping("/customization-values/{valueId}")
    public CustomizationOptionValueDto updateValue(@PathVariable Long valueId, @RequestBody CustomizationOptionValueDto dto) {
        return valueService.updateValue(valueId, dto);
    }

    @DeleteMapping("/customization-values/{valueId}")
    public void deleteValue(@PathVariable Long valueId) {
        valueService.deleteValue(valueId);
    }


}
