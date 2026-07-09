package com.ecommerce.paymentservice.dto;

import com.ecommerce.paymentservice.enums.PaymentProvider;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreatePaymentRequest {

    @NotNull
    private String orderId;

    @NotNull
    @Positive
    private BigDecimal amount;

    @Builder.Default
    private String currency = "INR";

    @NotNull
    private PaymentProvider provider;
}
