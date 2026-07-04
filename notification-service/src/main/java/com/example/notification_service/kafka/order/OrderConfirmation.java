package com.example.notification_service.kafka.order;



import com.example.notification_service.kafka.payment.PaymentMethod;
import com.example.notification_service.kafka.payment.Product;

import java.math.BigDecimal;
import java.util.List;

public record OrderConfirmation(
        String orderReference,
        BigDecimal totalAmount,
        PaymentMethod paymentMethod,
        Customer customer,
        List<Product> products
) {
}
