package com.keita.musicbay.service;

import com.keita.musicbay.model.Customer;
import com.keita.musicbay.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

    @Mock
    CustomerRepository customerRepository;

    @InjectMocks
    CustomerService customerService;

    @Test
    void saveCustomer(){
        //ARRANGE
        Customer customer1 = Customer.builder().userName("ice").email("ice@gmail.com").build();
        when(customerRepository.existsByEmail(customer1.getEmail()) && customerRepository.existsByUserName(customer1.getUserName())).thenReturn(false);

        Customer customer2 = Customer.builder().userName("brrr").email("brrr@gmail.com").build();
        when(customerRepository.existsByEmail(customer2.getEmail()) && customerRepository.existsByUserName(customer2.getUserName())).thenReturn(true);

        when(customerRepository.save(any(Customer.class))).thenReturn(new Customer());
        //ACT
        boolean customer1HasBeenSaved = customerService.saveCustomer(customer1);
        boolean customer2HasNotBeenSaved = customerService.saveCustomer(customer2);

        //ASSERT
        assertTrue(customer1HasBeenSaved);
        assertFalse(customer2HasNotBeenSaved);
    }
}
