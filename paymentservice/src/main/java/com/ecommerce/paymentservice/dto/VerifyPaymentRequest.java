package com.ecommerce.paymentservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VerifyPaymentRequest {
    @NotNull
    private Long paymentId;

    @NotBlank
    private String gatewayPaymentId;

    @NotBlank
    private String gatewaySignature;
}
