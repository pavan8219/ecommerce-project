package com.ecommerce.paymentservice.enums;

import java.util.Arrays;

public enum RazorpayWebhookEvent {
    PAYMENT_CAPTURED("payment.captured"),
    PAYMENT_FAILED("payment.failed");

    private final String eventName;

    RazorpayWebhookEvent(String eventName) {
        this.eventName = eventName;
    }

    public String getEventName() {
        return eventName;
    }

    public static RazorpayWebhookEvent from(String event) {

        return Arrays.stream(values())
                .filter(e -> e.eventName.equals(event))
                .findFirst()
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Unsupported event : " + event));
    }
}
