package com.keita.musicbay.repository;

import com.keita.musicbay.model.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
public class CustomerRepositoryTest {

    @Autowired
    CustomerRepository customerRepository;

    @BeforeEach
    void insert(){
        List<Customer> customers = Arrays.asList(
                Customer.builder().email("cancre@gmail.com").userName("bigWolf22").build()
        );
        customerRepository.saveAll(customers);
    }

    @Test
    void existsByUserName(){
        //ARRANGE
        Customer customer1 = Customer.builder().userName("sdaasdas").build();
        Customer customer2 = Customer.builder().userName("bigWolf22").build();

        //ACT
        boolean customerDoNotExist = customerRepository.existsByUserName(customer1.getUserName());
        boolean customerExist = customerRepository.existsByUserName(customer2.getUserName());

        //ASSERT
        assertFalse(customerDoNotExist);
        assertTrue(customerExist);
    }

    @Test
    void existsByEmail(){
        //ARRANGE
        Customer customer1 = Customer.builder().email("asdasd@gmail.com").build();
        Customer customer2 = Customer.builder().email("cancre@gmail.com").build();

        //ACT
        boolean customerDoNotExist = customerRepository.existsByEmail(customer1.getEmail());
        boolean customerExist = customerRepository.existsByEmail(customer2.getEmail());

        //ASSERT
        assertFalse(customerDoNotExist);
        assertTrue(customerExist);
    }


}
