package com.ecommerce.paymentservice.entities;
import com.ecommerce.paymentservice.enums.PaymentProvider;
import com.ecommerce.paymentservice.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Order for which payment is created
     */
    @Column(nullable = false, unique = true)
    private String orderId;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal amount;

    @Column(nullable = false)
    private String currency;

    /**
     * Which payment gateway handled this payment
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentProvider provider;

    /**
     * Current payment status
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus status;

    /**
     * Generic gateway identifiers
     */
    @Column(unique = true)
    private String gatewayOrderId;

    @Column(unique = true)
    private String gatewayPaymentId;

    @Column(length = 1000)
    private String gatewaySignature;

    /**
     * Failure reason (if any)
     */
    @Column(length = 1000)
    private String failureReason;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @PrePersist
    public void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = createdAt;
    }

    @PreUpdate
    public void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
