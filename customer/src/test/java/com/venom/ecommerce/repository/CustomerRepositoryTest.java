package com.venom.ecommerce.repository;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import com.venom.ecommerce.model.Customer;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
@Testcontainers
public class CustomerRepositoryTest {

    @Container
    @ServiceConnection
    static MongoDBContainer mongoDBContainer =
            new MongoDBContainer("mongo:8.0");

    @Autowired
    CustomerRepository repository;

    @Test
    void shouldSaveCustomer() {

        Customer customer = com.venom.ecommerce.model.Customer.builder()
                .firstname("Priyanshu")
                .lastname("Kushwaha")
                .email("abc@gmail.com")
                .build();

        Customer saved = repository.save(customer);

        assertThat(saved).isNotNull();
        assertThat(saved.getId()).isNotNull();

        Customer found = repository.findById(saved.getId())
                .orElseThrow();

        assertThat(found.getFirstname()).isEqualTo("Priyanshu");
        assertThat(found.getLastname()).isEqualTo("Kushwaha");
        assertThat(found.getEmail()).isEqualTo("abc@gmail.com");
    }
}
