package com.example.payment_service.service;


import com.example.common.payment.PaymentNotificationRequest;
import com.example.common.payment.PaymentRequest;
import com.example.common.payment.PaymentResponse;
import com.example.payment_service.kafka.NotificationProducer;
import com.example.payment_service.repository.PaymentRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
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

    public Integer createPayment(@Valid PaymentRequest request) throws JsonProcessingException {
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
