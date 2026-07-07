package com.example.customer_service.exception;

import com.example.customer_service.controller.CustomerController;
import com.example.customer_service.handler.GlobalExceptionHandler;
import com.example.customer_service.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@Slf4j
@WebMvcTest(CustomerController.class)
@Import(GlobalExceptionHandler.class)
public class CustomerExceptionTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CustomerService customerService;

    @Test
    void shouldReturn404WhenCustomerNotFound() throws Exception{
      when(customerService.findById(anyString()))
              .thenThrow(new CustomerNotFoundException("Customer Not Found"));

      mockMvc.perform(get("/api/v1/customers/123"))
              .andExpect(status().isNotFound())
              .andExpect(content().string("Customer Not Found"));

    }

    @Test
    void shouldReturnValidationErrors() throws Exception {

        String request = """
                {
                    "firstname": null,
                    "lastname": null,
                    "email": "invalid-email"
                }
                """;


            mockMvc.perform(post("/api/v1/customers/create")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(request))
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.errors.firstname")
                            .value("Customer first name cannot be null"))
                    .andExpect(jsonPath("$.errors.lastname")
                            .value("Customer last name cannot be null"))
                    .andExpect(jsonPath("$.errors.email")
                            .value("Customer email is not valid"));




    }

}
