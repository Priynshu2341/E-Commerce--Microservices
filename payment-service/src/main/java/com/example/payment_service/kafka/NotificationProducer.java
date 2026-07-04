package com.example.payment_service.kafka;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;


    public void sendNotification(PaymentNotificationRequest request){
        log.info("Sending Notification with body <{}>", request);
        String json = objectMapper.writeValueAsString(request);
        Message<String> message= MessageBuilder
                .withPayload(json)
                .setHeader(KafkaHeaders.TOPIC,"payment-topic")
                .build();

        kafkaTemplate.send(message);
    }
}
