package com.venom.ecommerce.service;

import com.venom.ecommerce.dto.CustomerRequest;
import com.venom.ecommerce.dto.CustomerResponse;
import com.venom.ecommerce.exception.CustomerNotFoundException;
import com.venom.ecommerce.model.Customer;
import com.venom.ecommerce.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private CustomerMapper customerMapper;

    @InjectMocks
    private CustomerService customerService;

    @Test
    void shouldCreateCustomer(){
        CustomerRequest request = new CustomerRequest(
                null,
                "Priyanshu",
                "Kushwaha",
                "abc@gmail.com",
                null
        );

        Customer customer = Customer.builder()
                .id("123")
                .firstname("Priyanshu")
                .lastname("Kushwaha")
                .email("abc@gmail.com")
                .build();

        when(customerMapper.toCustomer(request)).thenReturn(customer);
        when(customerRepository.save(customer)).thenReturn(customer);

        String id = customerService.createCustomer(request);
        assertThat(id).isEqualTo("123");
        verify(customerMapper).toCustomer(request);
        verify(customerRepository).save(customer);
    }

    @Test
    void shouldFindCustomerById(){
        Customer customer = Customer.builder()
                .id("123")
                .firstname("Priyanshu")
                .lastname("Kushwaha")
                .email("abc@gmail.com")
                .build();

        CustomerResponse response =
                new CustomerResponse(
                        "123",
                        "Priyanshu",
                        "Kushwaha",
                        "abc@gmail.com",
                        null
                );

        when(customerRepository.findById("123")).thenReturn(Optional.of(customer));
        when(customerMapper.fromCustomer(customer)).thenReturn(response);

        CustomerResponse result = customerService.findById("123");
        assertNotNull(result);
        assertThat(result.id()).isEqualTo("123");
        assertThat(result.firstname()).isEqualTo("Priyanshu");
        assertThat(result.lastname()).isEqualTo("Kushwaha");
        assertThat(result.email()).isEqualTo("abc@gmail.com");

        verify(customerRepository).findById("123");
        verify(customerMapper).fromCustomer(customer);

    }

    @Test
    void shouldThrowExceptionWhenCustomerNotFound() {

        when(customerRepository.findById("123"))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> customerService.findById("123"))
                .isInstanceOf(CustomerNotFoundException.class)
                .hasMessage("Invalid Customer id123");

        verify(customerRepository).findById("123");
        verify(customerMapper, never()).fromCustomer(any());

        verifyNoMoreInteractions(customerRepository, customerMapper);
    }


    @Test
    void shouldUpdateCustomer(){
        Customer customer = Customer.builder()
                .id("123")
                .firstname("Priyanshu")
                .lastname("Kushwaha")
                .email("abc@gmail.com")
                .build();

        CustomerRequest request =
                new CustomerRequest(
                        "123",
                        "Priyanshu1",
                        "Kushwaha2",
                        "abc@gmail.com1",
                        null
                );


        when(customerRepository.findById("123")).thenReturn(Optional.of(customer));
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);
        customerService.updateCustomer(request);

        assertThat(customer.getId()).isEqualTo("123");
        assertThat(customer.getFirstname()).isEqualTo("Priyanshu1");
        assertThat(customer.getLastname()).isEqualTo("Kushwaha2");
        assertThat(customer.getEmail()).isEqualTo("abc@gmail.com1");

        verify(customerRepository).findById("123");
        verify(customerRepository).save(customer);
        verifyNoMoreInteractions(customerRepository,customerMapper);
    }

    @Test
    void shouldDeleteCustomer(){
        Customer customer = Customer.builder()
                .id("123")
                .firstname("Priyanshu")
                .lastname("Kushwaha")
                .email("abc@gmail.com")
                .build();

        String id = "123";


        when(customerRepository.findById("123")).thenReturn(Optional.of(customer));
        customerService.deleteCustomer(id);
        verify(customerRepository).findById("123");
        verify(customerRepository).deleteById(id);
        verifyNoMoreInteractions(customerRepository,customerMapper);
    }

    @Test
    void shouldReturnAllCustomers(){
        Customer customer1 = Customer.builder()
                .id("1")
                .firstname("Priyanshu")
                .lastname("Kushwaha")
                .email("priyanshu@gmail.com")
                .build();

        Customer customer2 = Customer.builder()
                .id("2")
                .firstname("Rahul")
                .lastname("Sharma")
                .email("rahul@gmail.com")
                .build();

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

        when(customerRepository.findAll()).thenReturn(List.of(customer1,customer2));
        when(customerMapper.fromCustomer(customer1)).thenReturn(response1);
        when(customerMapper.fromCustomer(customer2)).thenReturn(response2);

        List<CustomerResponse> responses = customerService.findAllCustomer();

        assertNotNull(responses);
        assertThat(responses.size()).isEqualTo(2);
        assertThat(responses.get(0).firstname()).isEqualTo("Priyanshu");
        assertThat(responses.get(0).lastname()).isEqualTo("Kushwaha");
        assertThat(responses.get(1).firstname()).isEqualTo("Rahul");
        assertThat(responses.get(1).lastname()).isEqualTo("Sharma");

        verify(customerRepository).findAll();
        verify(customerMapper).fromCustomer(customer1);
        verify(customerMapper).fromCustomer(customer2);
        verifyNoMoreInteractions(customerRepository,customerMapper);
    }


}
