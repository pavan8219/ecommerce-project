package com.ecommerce.orderservice.events;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderPlacedEvent {
    private String eventId;
    private String orderId;
    private String skuCode;
    private Integer quantity;
    private LocalDateTime eventTime;
}
