package com.ecommerce.orderservice.kafka;

import com.ecommerce.orderservice.enums.PaymentProvider;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PaymentEvent {
    private Long paymentId;
    private String orderId;
    private String transactionId;
    private PaymentProvider provider;
    private BigDecimal amount;
}
