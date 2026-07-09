package com.ecommerce.inventoryservice.clients;

import com.ecommerce.inventoryservice.dto.OrderResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ORDERSERVICE")
public interface OrderClient {
    @GetMapping("/api/orders/{orderNumber}")
    OrderResponse getSkuCodeByOrderId(@PathVariable String orderNumber);
}
