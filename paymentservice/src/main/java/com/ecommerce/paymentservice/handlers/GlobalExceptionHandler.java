package com.ecommerce.paymentservice.handlers;

import com.ecommerce.paymentservice.exception.PaymentExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(PaymentExistsException.class)
    public ResponseEntity<?> handlePaymentAlreadyExistsException(PaymentExistsException exception) {
        Map<String,Object> error=Map.of("error","conflict","message",exception.getLocalizedMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);


    }
}
