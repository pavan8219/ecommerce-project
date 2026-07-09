package com.ecommerce.paymentservice.gateway.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GatewayVerificationRequest {
    private String gatewayOrderId;

    private String gatewayPaymentId;

    private String gatewaySignature;
}
