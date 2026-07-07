package com.example.order_service.kafka;

import com.example.common.order.OrderConfirmation;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Slf4j
public class OrderProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public void sendOrderConformation(OrderConfirmation orderConfirmation) throws JsonProcessingException {

            String json = objectMapper.writeValueAsString(orderConfirmation);
            log.info("Sending Order confirmation ");
            Message<String> message = MessageBuilder
                    .withPayload(json)
                    .setHeader(KafkaHeaders.TOPIC, "order-topic")
                    .build();
            kafkaTemplate.send(message);
            log.info("order-sent");


    }
}
