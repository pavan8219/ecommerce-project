package com.ecommerce.inventoryservice.kafka;

import com.ecommerce.inventoryservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderEventsConsumer {
//    private final InventoryService inventoryService;
//    @KafkaListener(topics = "order-events",groupId = "inventory-group-v2")
//    public void consume(OrderPlacedEvent event){
//        inventoryService.updateInventoryStock(event);
//    }
}
