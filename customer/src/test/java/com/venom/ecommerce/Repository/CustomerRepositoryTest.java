package com.venom.ecommerce.Repository;

import com.venom.ecommerce.model.Customer;
import com.venom.ecommerce.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@DataMongoTest
@Testcontainers
public class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository repository;

    @Container
    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:8.0");

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }

    @Test
    void shouldSaveCustomer(){
        Customer customer = Customer.builder()
                .firstname("Priyanshu")
                .lastname("Kushwaha")
                .email("abc@gmail.com")
                .build();

        Customer saved = repository.save(customer);
        assertNotNull(saved);
        Customer found = repository.findById(saved.getId()).orElseThrow();
        assertThat(found.getFirstname()).isEqualTo("Priyanshu");
        assertThat(saved.getLastname()).isEqualTo("Kushwaha");
        assertThat(saved.getEmail()).isEqualTo("abc@gmail.com");


        
    }


}
