package com.ecommerce.paymentservice.gateway.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GatewayPaymentResponse {

    private String gatewayOrderId;
    /**
     * Frontend uses this key
     */
    private String publicKey;
    private BigDecimal amount;
    private String currency;
    private Map<String, Object> additionalData;

}
