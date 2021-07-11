package com.keita.musicbay.repository;

import com.keita.musicbay.model.Customer;
import com.keita.musicbay.model.Purchasing;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
public class PurchasingRepositoryTest {
    @Autowired
    PurchasingRepository purchasingRepository;

    @Autowired
    CustomerRepository customerRepository;

    @BeforeEach
    void insert(){
        Customer customer = Customer.builder().username("brr").build();
        List<Purchasing> purchasings = Arrays.asList(
                Purchasing.builder().customer(customer).build(),
                Purchasing.builder().customer(customer).build(),
                Purchasing.builder().customer(customer).build()
        );
        purchasings.forEach(purchasing -> purchasing.setPurchasingDate(LocalDateTime.now()));

        customer.setPurchasings(purchasings);

        customerRepository.saveAndFlush(customer);
    }

    @Test
    void getByCustomerAndPurchasingDateBetween(){
        //ARRANGE
        Customer customer = customerRepository.findByUsername("brr").get();

        //ACT
        List<Purchasing> purchasings = purchasingRepository.getByCustomerAndPurchasingDateBetween(customer,LocalDateTime.now().minusDays(2),LocalDateTime.now().plusDays(2));

        //ASSERT
        Assertions.assertEquals(3,purchasings.size());

    }
}
