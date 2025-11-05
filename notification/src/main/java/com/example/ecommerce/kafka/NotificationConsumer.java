package com.example.ecommerce.kafka;

import com.example.ecommerce.email.EmailService;
import com.example.ecommerce.entity.Notifications;
import com.example.ecommerce.kafka.order.OrderConfirmation;
import com.example.ecommerce.kafka.payment.PaymentNotificationRequest;
import com.example.ecommerce.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static com.example.ecommerce.entity.NotificationsType.ORDER_CONFIRMATION;
import static com.example.ecommerce.entity.NotificationsType.PAYMENT_CONFIRMATION;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationConsumer {

    private final NotificationRepository repository;
    private final EmailService emailService;

    @KafkaListener(topics = "payment-topic")
    public void consumerPaymentSuccessNotification(PaymentNotificationRequest pr) {
        log.info("Consuming payment message: {}", pr);

        repository.save(
                Notifications.builder()
                        .notificationsType(PAYMENT_CONFIRMATION)
                        .createdAt(LocalDateTime.now())
                        .paymentNotificationRequest(pr)
                        .build()
        );

        emailService.sendPaymentSuccessEmail(pr);
        log.info("payment email sent ");
    }

    @KafkaListener(topics = "order-topic")
    public void consumeOrderConfirmationNotification(OrderConfirmation oc) {
        log.info("Consuming order message: {}", oc);

        repository.save(
                Notifications.builder()
                        .notificationsType(ORDER_CONFIRMATION)
                        .createdAt(LocalDateTime.now())
                        .orderConfirmation(oc)
                        .build()
        );

        emailService.sendOrderSuccessEmail(oc);
        log.info("order email sent ");
    }
}
