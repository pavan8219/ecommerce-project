package com.ecommerce.paymentservice.controller;

import com.ecommerce.paymentservice.service.PaymentWebhookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/payments/webhook")
@Slf4j
public class PaymentWebhookController {
    private final PaymentWebhookService paymentWebhookService;

    @PostMapping("/razorpay")
    public ResponseEntity<Void> handleWebhook(@RequestBody String payload, @RequestHeader("X-Razorpay-Signature") String signature) {
        System.out.println("Razorpay called webhook");
        log.info("razorpay webhook payload {}",payload);
        log.info("razorpay webhook signature {}",signature);
        paymentWebhookService.handleWebhook(payload, signature);
        return ResponseEntity.ok().build();
    }
}
