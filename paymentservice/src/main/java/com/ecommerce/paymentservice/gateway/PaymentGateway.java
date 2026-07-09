package com.ecommerce.paymentservice.gateway;

import com.ecommerce.paymentservice.enums.PaymentProvider;
import com.ecommerce.paymentservice.gateway.dto.GatewayPaymentRequest;
import com.ecommerce.paymentservice.gateway.dto.GatewayPaymentResponse;
import com.ecommerce.paymentservice.gateway.dto.GatewayVerificationRequest;
import com.ecommerce.paymentservice.gateway.dto.GatewayVerificationResponse;

public interface PaymentGateway {
    /**
     * Which provider does this implementation support?
     */
    PaymentProvider getProvider();

    String getPublicKey();

    /**
     * Creates an order in the payment gateway.
     */
    GatewayPaymentResponse createOrder(
            GatewayPaymentRequest request);

    /**
     * Verifies payment signature.
     */
    GatewayVerificationResponse verifyPayment(GatewayVerificationRequest request);

    void verifyWebhookSignature(String payload, String signature);
}
