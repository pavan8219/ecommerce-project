package com.ecommerce.orderservice.clients;

import com.ecommerce.orderservice.dto.InventoryResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "inventoryservice")
public interface InventoryClient {
    @GetMapping("/api/inventory/{skuCode}")
    InventoryResponse isInStock( @PathVariable("skuCode") String skuCode);
}
