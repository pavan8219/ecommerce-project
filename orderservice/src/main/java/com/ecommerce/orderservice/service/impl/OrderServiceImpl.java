package com.ecommerce.orderservice.service.impl;

import com.ecommerce.orderservice.clients.InventoryClient;
import com.ecommerce.orderservice.dto.InventoryResponse;
import com.ecommerce.orderservice.dto.OrderRequest;
import com.ecommerce.orderservice.dto.OrderResponse;
import com.ecommerce.orderservice.entity.Order;
import com.ecommerce.orderservice.events.OrderPlacedEvent;
import com.ecommerce.orderservice.kafka.OrderEventProducer;
import com.ecommerce.orderservice.kafka.PaymentEvent;
import com.ecommerce.orderservice.repository.OrderRepository;
import com.ecommerce.orderservice.service.OrderService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;
    private final InventoryClient inventoryClient;
    private final OrderEventProducer orderEventProducer;

    @Override
    @Transactional
    public OrderResponse placeOrder(OrderRequest orderRequest,Long userId) {
        InventoryResponse inventoryResponse=inventoryClient.isInStock(orderRequest.getSkuCode());
        if(!inventoryResponse.getAvailable()){
            throw new RuntimeException("Product is not in stock");
        }
        Order order=modelMapper.map(orderRequest,Order.class);
        order.setOrderNumber(UUID.randomUUID().toString());
        order.setOrderStatus("CREATED");
        order.setUserId(userId);
        Order saved=orderRepository.save(order);
        OrderPlacedEvent orderPlacedEvent=OrderPlacedEvent.builder()
                .eventId(UUID.randomUUID().toString())
                .orderId(saved.getOrderNumber())
                .skuCode(order.getSkuCode())
                .quantity(order.getQuantity())
                .eventTime(LocalDateTime.now())
                .build();
//        orderEventProducer.sendOrderPlacedEvent(orderPlacedEvent);
        return modelMapper.map(saved,OrderResponse.class);
    }

    @Override
    public void updateOrderStatus(PaymentEvent paymentEvent) {
        Order order=orderRepository.findByOrderNumber(paymentEvent.getOrderId()).orElseThrow(()->
                new RuntimeException("Order not found with order number "+paymentEvent.getOrderId()));
        order.setOrderStatus("PLACED");
        orderRepository.save(order);
    }

    public OrderResponse getOrderByOrderNumber(String orderNumber){
        Order order=orderRepository.findByOrderNumber(orderNumber).orElseThrow(()->
                new RuntimeException("Order not found with order number "+orderNumber));
        return modelMapper.map(order,OrderResponse.class);
    }
}
