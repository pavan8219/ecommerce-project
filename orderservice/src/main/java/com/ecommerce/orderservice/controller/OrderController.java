package com.ecommerce.orderservice.controller;

import com.ecommerce.orderservice.dto.OrderRequest;
import com.ecommerce.orderservice.dto.OrderResponse;
import com.ecommerce.orderservice.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;
    @PostMapping
    public ResponseEntity<OrderResponse> placeOrder(@RequestBody @Valid OrderRequest orderRequest,@RequestHeader("X-User-Id") Long userId){
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.placeOrder(orderRequest,userId));
    }

    @GetMapping("/{orderNumber}")
    public ResponseEntity<OrderResponse> getSkuCodeByOrderId(@PathVariable String orderNumber){
        return ResponseEntity.ok(orderService.getOrderByOrderNumber(orderNumber));
    }
}
