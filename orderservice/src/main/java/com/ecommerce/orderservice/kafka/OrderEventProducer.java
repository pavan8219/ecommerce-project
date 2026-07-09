package com.ecommerce.orderservice.kafka;

import com.ecommerce.orderservice.events.OrderPlacedEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrderEventProducer {
    private final KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate;

    public OrderEventProducer(KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendOrderPlacedEvent(OrderPlacedEvent event){
        kafkaTemplate.send("order-events",event.getOrderId(),event);
    }
}
