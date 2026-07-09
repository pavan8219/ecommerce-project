package com.ecommerce.paymentservice.service;

import com.ecommerce.paymentservice.dto.CreatePaymentRequest;
import com.ecommerce.paymentservice.dto.CreatePaymentResponse;
import com.ecommerce.paymentservice.dto.VerifyPaymentRequest;
import com.ecommerce.paymentservice.dto.VerifyPaymentResponse;

public interface PaymentService {
    CreatePaymentResponse createPayment(CreatePaymentRequest request);
    VerifyPaymentResponse verifyPayment(VerifyPaymentRequest request);
}
