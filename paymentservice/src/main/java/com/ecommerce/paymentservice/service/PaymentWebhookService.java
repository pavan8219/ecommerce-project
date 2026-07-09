package com.ecommerce.paymentservice.service;

public interface PaymentWebhookService {
    void handleWebhook(String payload, String signature);
}
