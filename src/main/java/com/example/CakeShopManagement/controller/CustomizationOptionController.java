package com.example.CakeShopManagement.controller;

import com.example.CakeShopManagement.dto.CustomizationOptionDto;
import com.example.CakeShopManagement.entity.CustomizationOptionEntity;
import com.example.CakeShopManagement.service.CustomizationOptionService;
import com.example.CakeShopManagement.service.Impl.CustomizationOptionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employee")
public class CustomizationOptionController {

    @Autowired
    private final CustomizationOptionService customizationOptionService;

    public CustomizationOptionController(CustomizationOptionService customizationOptionService) {
        this.customizationOptionService = customizationOptionService;
    }


    @PostMapping("/customization-options")
    public ResponseEntity<CustomizationOptionDto> addOption(@RequestBody CustomizationOptionDto dto){
        return ResponseEntity.ok(customizationOptionService.addOption(dto));
    }

    @GetMapping("/customization-options")
    public List<CustomizationOptionDto> getAllOptions(){
        return customizationOptionService.getAllOptions();
    }

    @PutMapping("/customization-options/{id}")
    public ResponseEntity<CustomizationOptionDto> updateOption(@PathVariable Long id, @RequestBody CustomizationOptionDto dto){
        return ResponseEntity.ok(customizationOptionService.updateOptions(id,dto));
    }

    @DeleteMapping("/customization-options/{id}")
    public ResponseEntity<Void> deleteOption(@PathVariable Long id){
        try {
            customizationOptionService.deleteOption(id);
            return ResponseEntity.noContent().build();
        }
        catch (Exception e){
            return ResponseEntity.notFound().build();
        }

    }
}
