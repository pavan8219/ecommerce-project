package com.ecommerce.paymentservice.controller;

import com.ecommerce.paymentservice.dto.CreatePaymentRequest;
import com.ecommerce.paymentservice.dto.CreatePaymentResponse;
import com.ecommerce.paymentservice.dto.VerifyPaymentRequest;
import com.ecommerce.paymentservice.dto.VerifyPaymentResponse;
import com.ecommerce.paymentservice.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/payments")
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity<CreatePaymentResponse> createPayment(@Valid @RequestBody CreatePaymentRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(paymentService.createPayment(request));
    }

    @PostMapping("/verify")
    public ResponseEntity<VerifyPaymentResponse> verifyPayment(
            @Valid @RequestBody VerifyPaymentRequest request) {

        return ResponseEntity.ok(paymentService.verifyPayment(request));
    }

    @GetMapping
    public ResponseEntity<String> sayHello(){
        return ResponseEntity.ok("hello from payment service");
    }
}
