package com.ecommerce.paymentservice.exception;

public class PaymentExistsException extends RuntimeException{
    public PaymentExistsException(String message){
        super(message);
    }
}
