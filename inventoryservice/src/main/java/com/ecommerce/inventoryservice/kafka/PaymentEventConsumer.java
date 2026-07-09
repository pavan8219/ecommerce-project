package com.ecommerce.inventoryservice.kafka;

import com.ecommerce.inventoryservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PaymentEventConsumer {

    private final InventoryService inventoryService;
    @KafkaListener(topics = "payment-events",groupId = "inventory-group-v2")
    public void consume(PaymentEvent event){
        log.info("consuming payment event {}",event);
        inventoryService.updateInventoryStock(event);
    }
}
