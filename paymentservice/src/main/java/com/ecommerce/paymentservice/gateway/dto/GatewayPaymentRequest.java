package com.ecommerce.paymentservice.gateway.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GatewayPaymentRequest {
    private String orderId;
    private BigDecimal amount;
    private String currency;
    private String customerName;
    private String customerEmail;
    private String customerPhone;
}
