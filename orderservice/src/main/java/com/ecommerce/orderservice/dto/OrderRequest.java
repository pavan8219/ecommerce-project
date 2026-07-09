package com.ecommerce.orderservice.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {
    private String skuCode;
    @Min(value = 1,message = "quantity should be atleast 1")
    private Integer quantity;
}
