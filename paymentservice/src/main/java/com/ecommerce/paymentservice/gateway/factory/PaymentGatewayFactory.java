package com.ecommerce.paymentservice.gateway.factory;

import com.ecommerce.paymentservice.enums.PaymentProvider;
import com.ecommerce.paymentservice.gateway.PaymentGateway;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Component
public class PaymentGatewayFactory {
    private final Map<PaymentProvider, PaymentGateway> gateways = new EnumMap<>(PaymentProvider.class);

    public PaymentGatewayFactory(List<PaymentGateway> gatewayList) {
        gatewayList.forEach(gateway -> gateways.put(gateway.getProvider(), gateway));
    }

    public PaymentGateway getGateway(PaymentProvider provider) {
        PaymentGateway gateway = gateways.get(provider);
        if (gateway == null) {
            throw new IllegalArgumentException("No payment gateway found for provider : " + provider);
        }
        return gateway;
    }
}
