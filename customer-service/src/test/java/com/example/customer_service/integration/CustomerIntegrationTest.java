package com.example.customer_service.integration;

import com.example.customer_service.model.Customer;
import com.example.customer_service.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistrar;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;




@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
public class CustomerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CustomerRepository customerRepository;

    @Container
    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:8.0.4");

    @DynamicPropertySource
    static void configure(DynamicPropertyRegistry registry){
        registry.add("spring.data.mongodb.uri",mongoDBContainer::getReplicaSetUrl);
    }

    @Test
    void shouldCreateCustomer() throws Exception {
        String request = """
        {
            "firstname":"Priyanshu",
            "lastname":"Kushwaha",
            "email":"abc@gmail.com"
        }
        """;

        mockMvc.perform(post("/api/v1/customers/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request)
        ).andExpect(status().isCreated());

        assertEquals(1,customerRepository.count());

    }

    @Test
    void shouldFindCustomerById() throws Exception {
        Customer customer = Customer.builder()
                .firstname("Priyanshu")
                .lastname("Kushwaha")
                .email("abc@gmail.com")
                .build();

        String customerId = customerRepository.save(customer)
                .getId();

        mockMvc.perform(get("/api/v1/customers/" + customerId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstname").value("Priyanshu"))
                .andExpect(jsonPath("$.lastname").value("Kushwaha"))
                .andExpect(jsonPath("$.email").value("abc@gmail.com"));

    }


}
