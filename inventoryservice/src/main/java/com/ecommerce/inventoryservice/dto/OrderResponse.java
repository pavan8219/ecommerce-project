package com.ecommerce.inventoryservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {
    private String orderNumber;
    private String skuCode;
    private String orderStatus;
    private Integer quantity;
}
