package com.example.CakeShopManagement.controller;

import com.example.CakeShopManagement.dto.StockDeductionDto;
import com.example.CakeShopManagement.dto.StockDto;
import com.example.CakeShopManagement.dto.StockTransactionDto;
import com.example.CakeShopManagement.service.StockService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employee")
@CrossOrigin
public class StockController {

    private final StockService stockService;

    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @PostMapping("/stock")
    public StockDto addStock(@RequestBody StockDto dto) {
        return stockService.addStock(dto);
    }

    @GetMapping("/stock")
    public List<StockDto> getAllStock() {
        return stockService.getAllStocks();
    }

    @GetMapping("/stock/history/{inventoryId}")
    public List<StockDto> getHistory(@PathVariable Long inventoryId) {
        return stockService.getStockHistory(inventoryId);
    }

//    @GetMapping("/stock/batch-number")
//    public ResponseEntity<String> getNextBatchNumber(){
//        return ResponseEntity.ok(stockService.getNextBatchNumber());
//    }

    @GetMapping("/stock/batch-number/{inventoryId}")
    public ResponseEntity<String> getNextBatchNumber(@PathVariable Long inventoryId){
        return ResponseEntity.ok(stockService.getNextBatchNumber(inventoryId));
    }

    @GetMapping("/stock/transactions/{inventoryId}")
    public ResponseEntity<List<StockTransactionDto>> getTransactions(@PathVariable Long inventoryId) {

        return ResponseEntity.ok(stockService.getStockTransactions(inventoryId));
    }

    @PutMapping("/stock/deduct/{stockId}")
    public ResponseEntity<StockDto> deductStock(@PathVariable Long stockId, @RequestBody StockDeductionDto dto) {
        return ResponseEntity.ok(stockService.deductStock(stockId, dto.getQuantity(),dto.getReason()));
    }
}
