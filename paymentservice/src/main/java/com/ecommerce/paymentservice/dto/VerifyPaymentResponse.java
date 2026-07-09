package com.ecommerce.paymentservice.dto;

import com.ecommerce.paymentservice.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VerifyPaymentResponse {
    private Long paymentId;

    private String orderId;

    private PaymentStatus status;

    private String message;
}
