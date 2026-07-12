package com.example.payment_service;

import com.example.common.payment.PaymentMethod;
import com.example.common.payment.PaymentNotificationRequest;
import com.example.payment_service.kafka.NotificationProducer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;

import java.math.BigDecimal;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class NotificationProducerTest {

    @Mock
    private KafkaTemplate<String, String> kafkaTemplate;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private NotificationProducer notificationProducer;

    @Captor
    private ArgumentCaptor<Message<String>> notificationCaptor;

    @Test
    void shouldSendNotification() throws JsonProcessingException {
        PaymentNotificationRequest request =
                new PaymentNotificationRequest(
                        "ORD-001",
                        BigDecimal.valueOf(2500),
                        PaymentMethod.CREDIT_CARD,
                        "priyanshu",
                        "kushwaha",
                        "priyanshu@gmail.com"
                );

        String json = """
                {
                  "id":1,
                  "reference":"ORD-001"
                }
                """;

        when(objectMapper.writeValueAsString(request)).thenReturn(json);
        notificationProducer.sendNotification(request);
        verify(kafkaTemplate).send(notificationCaptor.capture());
        Message<String> message = notificationCaptor.getValue();
        Assertions.assertEquals(json,message.getPayload());
        Assertions.assertEquals("payment-topic",message.getHeaders().get(KafkaHeaders.TOPIC));
    }
}
