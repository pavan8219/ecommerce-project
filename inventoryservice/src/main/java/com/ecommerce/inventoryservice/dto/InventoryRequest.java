package com.ecommerce.inventoryservice.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InventoryRequest {
    @NotBlank(message = "skucode is required")
    private String skuCode;
    @Min(value = 0, message = "quantity should be 0 or more than 0")
    private Integer quantity;
}
