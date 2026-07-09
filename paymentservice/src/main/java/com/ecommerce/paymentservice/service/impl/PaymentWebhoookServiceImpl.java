package com.ecommerce.paymentservice.service.impl;

import com.ecommerce.paymentservice.entities.Payment;
import com.ecommerce.paymentservice.enums.PaymentProvider;
import com.ecommerce.paymentservice.enums.PaymentStatus;
import com.ecommerce.paymentservice.enums.RazorpayWebhookEvent;
import com.ecommerce.paymentservice.events.PaymentEvent;
import com.ecommerce.paymentservice.gateway.PaymentGateway;
import com.ecommerce.paymentservice.gateway.factory.PaymentGatewayFactory;
import com.ecommerce.paymentservice.kafka.PaymentEventProducer;
import com.ecommerce.paymentservice.repository.PaymentRepository;
import com.ecommerce.paymentservice.service.PaymentWebhookService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentWebhoookServiceImpl implements PaymentWebhookService {
    private final ObjectMapper objectMapper;
    private final PaymentRepository paymentRepository;
    private final PaymentGatewayFactory paymentGatewayFactory;
    private final PaymentEventProducer producer;

    @Override
    @Transactional
    public void handleWebhook(String payload, String signature) {
        PaymentGateway gateway = paymentGatewayFactory.getGateway(PaymentProvider.RAZORPAY);
        gateway.verifyWebhookSignature(payload, signature);
        JsonNode root;
        try {
            root = objectMapper.readTree(payload);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Invalid JSON payload", e);
        }
        String event = root.path("event").asText();
        JsonNode paymentNode = root.path("payload")
                        .path("payment")
                        .path("entity");
        String gatewayOrderId = paymentNode.get("order_id").asText();
        String gatewayPaymentId = paymentNode.get("id").asText();
        String status = paymentNode.get("status").asText();
        Payment payment = paymentRepository
                        .findByGatewayOrderId(gatewayOrderId)
                        .orElseThrow(() -> new RuntimeException("Payment not found"));

        if (payment.getStatus() == PaymentStatus.SUCCESS) {
            return;
        }
        RazorpayWebhookEvent webhookEvent = RazorpayWebhookEvent.from(event);
        switch (webhookEvent) {
            case PAYMENT_CAPTURED -> {
                payment.setStatus(PaymentStatus.SUCCESS);
                payment.setGatewayPaymentId(gatewayPaymentId);
                paymentRepository.save(payment);
                PaymentEvent paymentSuccessfulEvent= PaymentEvent.builder()
                        .paymentId(payment.getId())
                        .orderId(payment.getOrderId())
                        .amount(payment.getAmount())
                        .provider(payment.getProvider())
                        .transactionId(payment.getGatewayPaymentId())
                        .build();

                log.info(" calling publish event to kafka broker {}",event);
                producer.sendPaymentSuccessfulEvent(paymentSuccessfulEvent);
            }
            case PAYMENT_FAILED -> {
                payment.setStatus(PaymentStatus.FAILED);
                paymentRepository.save(payment);
            }
        }
    }
}
