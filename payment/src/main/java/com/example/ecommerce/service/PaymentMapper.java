package com.example.ecommerce.service;

import com.example.ecommerce.dtos.PaymentRequest;
import com.example.ecommerce.dtos.PaymentResponse;
import com.example.ecommerce.entity.Payment;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

@Service
public class PaymentMapper {


    public Payment toPayment(@Valid PaymentRequest request) {
        return Payment
                .builder()
                .orderID(request.orderId())
                .paymentMethod(request.paymentMethod())
                .amount(request.amount())
                .build();
    }

    public PaymentResponse toPaymentResponse(Payment payment) {
        return new PaymentResponse(
                payment.getAmount(),
                payment.getPaymentMethod(),
                payment.getOrderID()
        );
    }
}
