package com.example.CakeShopManagement.controller;

import com.example.CakeShopManagement.dto.InventoryDto;
import com.example.CakeShopManagement.service.InventoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/employee")
public class InventoryController {
    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @PostMapping("/inventory")
    public ResponseEntity<InventoryDto> addItem(@RequestBody InventoryDto dto){
        return ResponseEntity.ok(inventoryService.addItem(dto));
    }

    @GetMapping("/inventory")
    public ResponseEntity<List<InventoryDto>> getAllItems(){
        return ResponseEntity.ok(inventoryService.getAllItems());
    }

    @GetMapping("/inventory/{id}")
    public ResponseEntity<InventoryDto> getItem(@PathVariable Long id){
        return ResponseEntity.ok(inventoryService.getItem(id));
    }

    @GetMapping("/inventory/itemSku")
    public ResponseEntity<String> getItemSku(){
        return ResponseEntity.ok(inventoryService.getItemBySku());
    }

    @PutMapping("/inventory/{id}")
    public ResponseEntity<InventoryDto> updateItem(@PathVariable Long id, @RequestBody InventoryDto dto){
        return ResponseEntity.ok(inventoryService.updateItem(id, dto));
    }

    @DeleteMapping("/inventory/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long id){
        inventoryService.deleteItem(id);
        return ResponseEntity.noContent().build();
    }
}
