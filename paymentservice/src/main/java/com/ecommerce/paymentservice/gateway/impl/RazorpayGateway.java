package com.ecommerce.paymentservice.gateway.impl;

import com.ecommerce.paymentservice.enums.PaymentProvider;
import com.ecommerce.paymentservice.gateway.PaymentGateway;
import com.ecommerce.paymentservice.gateway.dto.GatewayPaymentRequest;
import com.ecommerce.paymentservice.gateway.dto.GatewayPaymentResponse;
import com.ecommerce.paymentservice.gateway.dto.GatewayVerificationRequest;
import com.ecommerce.paymentservice.gateway.dto.GatewayVerificationResponse;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.Utils;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class RazorpayGateway implements PaymentGateway {

    private final RazorpayClient razorpayClient;
    @Value("${razorpay.key-id}")
    private String keyId;
    @Value("${razorpay.key-secret}")
    private String keySecret;

    @Value("${razorpay.webhook.secret}")
    private String webhookSecret;
    /**
     * Which provider does this implementation support?
     */
    @Override
    public PaymentProvider getProvider() {
        return PaymentProvider.RAZORPAY;
    }

    @Override
    public String getPublicKey() {
        return keyId;
    }

    /**
     * Creates an order in the payment gateway.
     *
     * @param request
     */
    @Override
    public GatewayPaymentResponse createOrder(GatewayPaymentRequest request) {
        try {
            JSONObject options = new JSONObject();
            options.put("amount", request.getAmount().multiply(BigDecimal.valueOf(100)).longValue());
            options.put("currency", request.getCurrency());
            options.put("receipt", request.getOrderId().toString());
            Order order = razorpayClient.orders.create(options);
            return GatewayPaymentResponse.builder()
                    .gatewayOrderId(order.get("id"))
                    .publicKey(keyId)
                    .amount(request.getAmount())
                    .currency(request.getCurrency())
                    .build();
        } catch (Exception ex) {
            throw new RuntimeException("Unable to create Razorpay Order", ex);
        }
    }

    /**
     * Verifies payment signature.
     *
     * @param request
     */
    @Override
    public GatewayVerificationResponse verifyPayment(GatewayVerificationRequest request) {
        try {
            JSONObject options = new JSONObject();
            options.put("razorpay_order_id", request.getGatewayOrderId());
            options.put("razorpay_payment_id", request.getGatewayPaymentId());
            options.put("razorpay_signature", request.getGatewaySignature());

            Utils.verifyPaymentSignature(options, keySecret);
            return GatewayVerificationResponse
                    .builder()
                    .verified(true)
                    .gatewayPaymentId(request.getGatewayPaymentId())
                    .gatewaySignature(request.getGatewaySignature())
                    .build();

        } catch (Exception ex) {
            return GatewayVerificationResponse
                    .builder()
                    .verified(false)
                    .build();
        }
    }

    @Override
    public void verifyWebhookSignature(String payload, String signature) {
        try {
            Utils.verifyWebhookSignature(payload, signature, webhookSecret);
        } catch (Exception ex) {
            throw new RuntimeException("Invalid webhook signature", ex);
        }
    }
}
