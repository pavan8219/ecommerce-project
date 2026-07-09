package com.ecommerce.paymentservice.kafka;

import com.ecommerce.paymentservice.events.PaymentEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PaymentEventProducer {
    private final KafkaTemplate<String, PaymentEvent> kafkaTemplate;

    public PaymentEventProducer(KafkaTemplate<String, PaymentEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendPaymentSuccessfulEvent(PaymentEvent event){
        log.info("publishing event to kafka broker {}",event);
        kafkaTemplate.send("payment-events",event.getOrderId(),event);
    }
}
