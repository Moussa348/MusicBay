package com.keita.musicbay.repository;

import com.keita.musicbay.model.Customer;
import com.keita.musicbay.model.Sharing;
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
public class SharingRepositoryTest {

    @Autowired
    SharingRepository sharingRepository;

    @Autowired
    CustomerRepository customerRepository;

    @BeforeEach
    void insert(){
        Customer customer = Customer.builder().username("brr").build();
        List<Sharing> sharings = Arrays.asList(
                Sharing.builder().customer(customer).build(),
                Sharing.builder().customer(customer).build(),
                Sharing.builder().customer(customer).build()
        );

        customer.setSharings(sharings);

        customerRepository.saveAndFlush(customer);
    }

    @Test
    void getByCustomerAndSharingDateBetween(){
        //ARRANGE
        Customer customer = customerRepository.findByUsername("brr").get();

        //ACT
        List<Sharing> sharings = sharingRepository.getByCustomerAndSharingDateBetween(customer, LocalDateTime.now().minusDays(2),LocalDateTime.now().plusDays(2));

        //ASSERT
        Assertions.assertEquals(3,sharings.size());

    }
}
