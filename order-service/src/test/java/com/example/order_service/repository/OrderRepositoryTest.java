package com.example.order_service.repository;


import com.example.common.payment.PaymentMethod;
import com.example.order_service.entity.Orders;
import com.example.order_service.rep.OrderRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;

@Testcontainers
@DataJpaTest
public class OrderRepositoryTest {

    @Autowired
    private OrderRepository  orderRepository;

    @Container
    private static PostgreSQLContainer<?> postgreSQLContainer =
            new PostgreSQLContainer<>("postgres:17.6");

    @DynamicPropertySource
    private static void setPostgreSQLContainer(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);

    }

    @Test
    void shouldSaveOrder(){
        Orders order = Orders.builder()
                .reference("ORD-001")
                .customerID("customer-1")
                .paymentMethod(PaymentMethod.PAYPAL)
                .totalAmount(BigDecimal.valueOf(50000))
                .build();

        Orders savedOrder = orderRepository.save(order);
        Assertions.assertNotNull(savedOrder);
        Assertions.assertNotNull(savedOrder.getId());
        Assertions.assertEquals("customer-1",savedOrder.getCustomerID());
        Assertions.assertEquals(PaymentMethod.PAYPAL,savedOrder.getPaymentMethod());
        Assertions.assertEquals(0,BigDecimal.valueOf(50000).compareTo(order.getTotalAmount()));



    }
}
