package com.ecommerce.orderservice.service;

import com.ecommerce.orderservice.dto.OrderRequest;
import com.ecommerce.orderservice.dto.OrderResponse;
import com.ecommerce.orderservice.kafka.PaymentEvent;

public interface OrderService {
    OrderResponse placeOrder(OrderRequest orderRequest,Long userId);
    void updateOrderStatus(PaymentEvent paymentEvent);
    OrderResponse getOrderByOrderNumber(String orderNumber);
}
