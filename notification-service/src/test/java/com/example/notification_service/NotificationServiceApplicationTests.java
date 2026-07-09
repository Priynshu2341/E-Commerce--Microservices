package com.example.notification_service;

import com.example.notification_service.kafka.NotificationConsumer;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@SpringBootTest(properties = {
		"spring.kafka.bootstrap-servers=localhost:9092",
		"spring.cloud.config.enabled=false",
		"spring.kafka.listener.auto-startup=false"
})
class NotificationServiceApplicationTests {




}
