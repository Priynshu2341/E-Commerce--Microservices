package com.example.notification_service.kafka;


import com.example.common.order.OrderConfirmation;
import com.example.common.payment.PaymentNotificationRequest;
import com.example.notification_service.email.EmailService;
import com.example.notification_service.entity.Notifications;
import com.example.notification_service.repository.NotificationRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static com.example.notification_service.entity.NotificationsType.ORDER_CONFIRMATION;
import static com.example.notification_service.entity.NotificationsType.PAYMENT_CONFIRMATION;


@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationConsumer {

    private final NotificationRepository repository;
    private final EmailService emailService;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "payment-topic",groupId = "notification-group")
    public void consumerPaymentSuccessNotification(String pr) throws JsonProcessingException {
        log.info("Consuming payment message: {}", pr);

        PaymentNotificationRequest notificationRequest = objectMapper.readValue(pr,PaymentNotificationRequest.class);

        repository.save(
                Notifications.builder()
                        .notificationsType(PAYMENT_CONFIRMATION)
                        .createdAt(LocalDateTime.now())
                        .paymentNotificationRequest(notificationRequest)
                        .build()
        );

        emailService.sendPaymentSuccessEmail(notificationRequest);
        log.info("payment email sent ");
    }

    @KafkaListener(topics = "order-topic",groupId = "notification-group")
    public void consumeOrderConfirmationNotification(String pr) throws JsonProcessingException {
        log.info("Consuming order message: {}", pr);

        OrderConfirmation confirmation = objectMapper.readValue(pr,OrderConfirmation.class);

        repository.save(
                Notifications.builder()
                        .notificationsType(ORDER_CONFIRMATION)
                        .createdAt(LocalDateTime.now())
                        .orderConfirmation(confirmation)
                        .build()
        );

        emailService.sendOrderSuccessEmail(confirmation);
        log.info("order email sent ");
    }
}
