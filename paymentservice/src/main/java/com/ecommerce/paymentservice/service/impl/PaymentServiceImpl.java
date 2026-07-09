package com.ecommerce.paymentservice.service.impl;

import com.ecommerce.paymentservice.dto.CreatePaymentRequest;
import com.ecommerce.paymentservice.dto.CreatePaymentResponse;
import com.ecommerce.paymentservice.dto.VerifyPaymentRequest;
import com.ecommerce.paymentservice.dto.VerifyPaymentResponse;
import com.ecommerce.paymentservice.entities.Payment;
import com.ecommerce.paymentservice.enums.PaymentStatus;
import com.ecommerce.paymentservice.exception.PaymentExistsException;
import com.ecommerce.paymentservice.gateway.PaymentGateway;
import com.ecommerce.paymentservice.gateway.dto.GatewayPaymentRequest;
import com.ecommerce.paymentservice.gateway.dto.GatewayPaymentResponse;
import com.ecommerce.paymentservice.gateway.dto.GatewayVerificationRequest;
import com.ecommerce.paymentservice.gateway.dto.GatewayVerificationResponse;
import com.ecommerce.paymentservice.gateway.factory.PaymentGatewayFactory;
import com.ecommerce.paymentservice.repository.PaymentRepository;
import com.ecommerce.paymentservice.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentGatewayFactory paymentGatewayFactory;

    @Override
    public CreatePaymentResponse createPayment(CreatePaymentRequest request) {
        log.info("CreatePaymentRequest request {}",request);

        Optional<Payment> existingPayment = paymentRepository.findByOrderId(request.getOrderId());

        if (existingPayment.isPresent()) {
            return handleExistingPayment(existingPayment.get(), request);
        }

        return createFreshPayment(request);
    }

    private CreatePaymentResponse handleExistingPayment(
            Payment payment,
            CreatePaymentRequest request) {

        return switch (payment.getStatus()) {

            case SUCCESS ->
                    throw new PaymentExistsException(
                            "Payment already completed for order : "
                                    + payment.getOrderId());
            case REFUNDED ->
                    throw new PaymentExistsException(
                            "refund already completed for order : "
                                    + payment.getOrderId());

            case PENDING ->
                    buildResponse(payment, paymentGatewayFactory
                                    .getGateway(payment.getProvider())
                                    .getPublicKey());

            case FAILED, CANCELLED ->
                    createNewGatewayOrder(payment);
        };
    }

    private CreatePaymentResponse createFreshPayment(CreatePaymentRequest request) {

        PaymentGateway paymentGateway = paymentGatewayFactory.getGateway(request.getProvider());

        GatewayPaymentRequest gatewayRequest = GatewayPaymentRequest.builder()
                        .orderId(request.getOrderId())
                        .amount(request.getAmount())
                        .currency(request.getCurrency())
                        .build();

        GatewayPaymentResponse gatewayResponse = paymentGateway.createOrder(gatewayRequest);

        Payment payment = Payment.builder()
                .orderId(request.getOrderId())
                .amount(request.getAmount())
                .currency(request.getCurrency())
                .provider(request.getProvider())
                .status(PaymentStatus.PENDING)
                .gatewayOrderId(gatewayResponse.getGatewayOrderId())
                .build();

        System.out.println("OrderId before save: " + payment.getOrderId());

        payment = paymentRepository.save(payment);

        return buildResponse(payment, gatewayResponse.getPublicKey());
    }

    private CreatePaymentResponse createNewGatewayOrder(
            Payment payment) {

        PaymentGateway paymentGateway =
                paymentGatewayFactory.getGateway(payment.getProvider());

        GatewayPaymentRequest gatewayRequest =
                GatewayPaymentRequest.builder()
                        .orderId(payment.getOrderId())
                        .amount(payment.getAmount())
                        .currency(payment.getCurrency())
                        .build();

        GatewayPaymentResponse gatewayResponse =
                paymentGateway.createOrder(gatewayRequest);

        payment.setGatewayOrderId(gatewayResponse.getGatewayOrderId());
        payment.setStatus(PaymentStatus.PENDING);

        System.out.println("OrderId before save: " + payment.getOrderId());

        payment = paymentRepository.save(payment);

        return buildResponse(payment, gatewayResponse.getPublicKey());
    }

    private CreatePaymentResponse buildResponse(Payment payment, String publicKey) {

        return CreatePaymentResponse.builder()
                .paymentId(payment.getId())
                .gatewayOrderId(payment.getGatewayOrderId())
                .amount(payment.getAmount())
                .currency(payment.getCurrency())
                .status(payment.getStatus())
                .keyId(publicKey)
                .build();
    }

    @Override
    public VerifyPaymentResponse verifyPayment(VerifyPaymentRequest request) {

        Payment payment = paymentRepository.findById(request.getPaymentId())
                .orElseThrow(() -> new RuntimeException("Payment not found with id : " + request.getPaymentId()));

        PaymentGateway paymentGateway = paymentGatewayFactory.getGateway(payment.getProvider());

        GatewayVerificationRequest gatewayRequest =
                GatewayVerificationRequest.builder()
                        .gatewayOrderId(payment.getGatewayOrderId())
                        .gatewayPaymentId(request.getGatewayPaymentId())
                        .gatewaySignature(request.getGatewaySignature())
                        .build();

        GatewayVerificationResponse gatewayResponse = paymentGateway.verifyPayment(gatewayRequest);

        if (gatewayResponse.isVerified()) {
            payment.setStatus(PaymentStatus.SUCCESS);
            payment.setGatewayPaymentId(gatewayResponse.getGatewayPaymentId());
            payment.setGatewaySignature(gatewayResponse.getGatewaySignature());

        } else {
            payment.setStatus(PaymentStatus.FAILED);
            payment.setFailureReason("Signature verification failed");
        }

        paymentRepository.save(payment);

        return VerifyPaymentResponse.builder()
                .paymentId(payment.getId())
                .orderId(payment.getOrderId())
                .status(payment.getStatus())
                .message(gatewayResponse.isVerified()
                        ? "Payment verified successfully"
                        : "Payment verification failed")
                .build();
    }
}
