package com.ecommerce.orderservice.kafka;

import com.ecommerce.orderservice.repository.OrderRepository;
import com.ecommerce.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PaymentEventConsumer {

    private final OrderService orderService;
    @KafkaListener(topics = "payment-events",groupId = "payment-group-v1")
    public void consume(PaymentEvent event){
        log.info("consuming payment event in order service {}",event);
        orderService.updateOrderStatus(event);
    }
}
