package com.ecommerce.inventoryservice.controller;

import com.ecommerce.inventoryservice.dto.InventoryRequest;
import com.ecommerce.inventoryservice.dto.InventoryResponse;
import com.ecommerce.inventoryservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/inventory")
public class InventoryController {
    private final InventoryService inventoryService;

    @GetMapping("/{skuCode}")
    public ResponseEntity<InventoryResponse> isInStock(@PathVariable String skuCode){
        return ResponseEntity.ok(inventoryService.checkInventory(skuCode));
    }

    @PostMapping
    public ResponseEntity<InventoryResponse> addInventory(@RequestBody InventoryRequest inventoryRequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(inventoryService.addInventory(inventoryRequest));
    }

    @PutMapping("/{id}")
    public ResponseEntity<InventoryResponse> updateInventory(@PathVariable(name = "id") String skuCode,@RequestBody InventoryRequest inventoryRequest){
        return ResponseEntity.ok(inventoryService.updateInventory(skuCode,inventoryRequest));
    }

}
