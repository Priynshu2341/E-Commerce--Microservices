package com.example.customer_service.repository;


import com.example.customer_service.model.Customer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.Optional;


@DataMongoTest
@Testcontainers
public class CustomerRepositoryTest {


    private static final Logger log = LoggerFactory.getLogger(CustomerRepositoryTest.class);

    @Autowired
    private CustomerRepository customerRepository;

    @Container
    static MongoDBContainer mongoDBContainer = new MongoDBContainer(DockerImageName.parse("mongo:8.0.4"));

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }

    @Test
    void shouldSaveCustomer() {
        Customer customer = Customer.builder()
                .firstname("Priyanshu")
                .lastname("Kushwaha")
                .email("priyanshu@gmail.com")
                .build();

        Customer saved = customerRepository.save(customer);

        Assertions.assertNotNull(saved.getId());
        Assertions.assertEquals("Priyanshu", saved.getFirstname());
        log.info(saved.getId());
    }

    @Test
    void shouldFindCustomerById() {
        Customer customer = Customer.builder()
                .firstname("Priyanshu")
                .lastname("Kushwaha")
                .email("priyanshu@gmail.com")
                .build();

        Customer saved = customerRepository.save(customer);
        Optional<Customer> result = customerRepository.findById(saved.getId());
        Assertions.assertNotNull(customer);
        Assertions.assertEquals("Priyanshu", result.get().getFirstname());
        Assertions.assertEquals("Kushwaha", result.get().getLastname());
        Assertions.assertEquals("priyanshu@gmail.com", result.get().getEmail());
    }

}
