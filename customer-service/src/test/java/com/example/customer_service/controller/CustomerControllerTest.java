package com.example.customer_service.controller;

import com.example.common.customer.CustomerRequest;
import com.example.common.customer.CustomerResponse;
import com.example.customer_service.service.CustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Arrays;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CustomerService customerService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldCreateCustomer() throws Exception {
        CustomerRequest request = new CustomerRequest(
                null,
                "Priyanshu",
                "Kushwaha",
                "abc@gmail.com",
                null
        );

        when(customerService.createCustomer(any())).thenReturn("123");

        mockMvc.perform(
                        post("/api/v1/customers/create")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))

                )
                .andExpect(status().isOk())
                .andExpect(content().string("123"));

        verify(customerService).createCustomer(any(CustomerRequest.class));
        verifyNoMoreInteractions(customerService);
    }

    @Test
    void shouldUpdateCustomer() throws Exception {
        CustomerRequest request =
                new CustomerRequest(
                        "123",
                        "Priyanshu1",
                        "Kushwaha2",
                        "abc@gmail.com1",
                        null
                );

        doNothing().when(customerService).updateCustomer(any(CustomerRequest.class));


        mockMvc.perform(
                        put("/api/v1/customers/update")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(status().isOk())
                .andExpect(content().string(""));
        verify(customerService).updateCustomer(any(CustomerRequest.class));
        verifyNoMoreInteractions(customerService);
    }

    @Test
    void shouldFindCustomerById() throws Exception {
        CustomerResponse response = new CustomerResponse(
                "123",
                "Priyanshu",
                "Kushwaha",
                "abc@gmail.com",
                null
        );

        when(customerService.findById("123")).thenReturn(response);


        mockMvc.perform(
                        get("/api/v1/customers/123")
                                .contentType(MediaType.APPLICATION_JSON)

                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("123"))
                .andExpect(jsonPath("$.firstname").value("Priyanshu"))
                .andExpect(jsonPath("$.lastname").value("Kushwaha"))
                .andExpect(jsonPath("$.email").value("abc@gmail.com"));

        verify(customerService).findById("123");
        verifyNoMoreInteractions(customerService);


    }


    @Test
    void shouldDeleteCustomerById() throws Exception {


        String id = "123";


        doNothing().when(customerService).deleteCustomer(id);

        mockMvc.perform(
                        delete("/api/v1/customers/delete/123")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNoContent());

        verify(customerService).deleteCustomer(id);
        verifyNoMoreInteractions(customerService);


    }


    @Test
    void shouldFindAllCustomers() throws Exception {

        CustomerResponse response1 = new CustomerResponse(
                "1",
                "Priyanshu",
                "Kushwaha",
                "priyanshu@gmail.com",
                null
        );

        CustomerResponse response2 = new CustomerResponse(
                "2",
                "Rahul",
                "Sharma",
                "rahul@gmail.com",
                null
        );

        when(customerService.findAllCustomer()).thenReturn(Arrays.asList(response1, response2));

        mockMvc.perform(
                        get("/api/v1/customers/get")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstname").value("Priyanshu"))
                .andExpect(jsonPath("$[0].lastname").value("Kushwaha"))
                .andExpect(jsonPath("$[1].firstname").value("Rahul"))
                .andExpect(jsonPath("$[1].lastname").value("Sharma"));

        verify(customerService).findAllCustomer();
        verifyNoMoreInteractions(customerService);

    }
}
