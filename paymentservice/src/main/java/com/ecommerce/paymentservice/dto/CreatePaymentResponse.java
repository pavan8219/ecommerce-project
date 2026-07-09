package com.ecommerce.paymentservice.dto;

import com.ecommerce.paymentservice.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreatePaymentResponse {
    private Long paymentId;
    private String gatewayOrderId;
    private BigDecimal amount;
    private String currency;
    private PaymentStatus status;
    /**
     * Needed by frontend later
     */
    private String keyId;
}
