package com.example.ecommerce.service;

import com.example.ecommerce.dtos.PaymentRequest;
import com.example.ecommerce.dtos.PaymentResponse;
import com.example.ecommerce.kafka.NotificationProducer;
import com.example.ecommerce.kafka.PaymentNotificationRequest;
import com.example.ecommerce.repository.PaymentRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository repository;
    private final PaymentMapper mapper;
    private final NotificationProducer notificationProducer;

    public Integer createPayment(@Valid PaymentRequest request) {
        log.info("saving payment Request {}", request);
        var payment = repository.save(mapper.toPayment(request));

        var notification = new PaymentNotificationRequest(
                request.orderReference(),
                request.amount(),
                request.paymentMethod(),
                request.customer().firstname(),
                request.customer().lastname(),
                request.customer().email()

        );
        log.info("➡️ Sending Kafka PaymentNotificationRequest payload: {}", notification);
        notificationProducer.sendNotification(notification);
        return payment.getPaymentId();
    }

    public List<PaymentResponse> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toPaymentResponse).toList();
    }
}
