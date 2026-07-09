package com.ecommerce.paymentservice.gateway.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GatewayVerificationResponse {
    private boolean verified;

    private String gatewayPaymentId;

    private String gatewaySignature;
}
