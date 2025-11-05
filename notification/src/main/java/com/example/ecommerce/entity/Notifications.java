package com.example.ecommerce.entity;

import com.example.ecommerce.kafka.order.OrderConfirmation;
import com.example.ecommerce.kafka.payment.PaymentNotificationRequest;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@Document
public class Notifications {

    @Id
    private String id;
    private NotificationsType notificationsType;
    private LocalDateTime createdAt;
    private OrderConfirmation orderConfirmation;
    private PaymentNotificationRequest paymentNotificationRequest;
}
