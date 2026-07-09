package com.ecommerce.paymentservice.repository;

import com.ecommerce.paymentservice.entities.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment,Long> {
    Optional<Payment> findByOrderId(String orderId);

    Optional<Payment> findByGatewayOrderId(String gatewayOrderId);

    Optional<Payment> findByGatewayPaymentId(String gatewayPaymentId);
}
