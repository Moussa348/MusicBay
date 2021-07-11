package com.keita.musicbay.repository;

import com.keita.musicbay.model.Customer;
import com.keita.musicbay.model.Liking;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
public class LikingRepositoryTest {

    @Autowired
    LikingRepository likingRepository;

    @Autowired
    CustomerRepository customerRepository;

    @BeforeEach
    void insert(){
        Customer customer = Customer.builder().username("brr").build();
        List<Liking> likings = Arrays.asList(
                Liking.builder().customer(customer).build(),
                Liking.builder().customer(customer).build(),
                Liking.builder().customer(customer).build()
        );

        likings.get(2).setLikingDate(LocalDateTime.now().plusDays(2));
        customer.setLikings(likings);

        customerRepository.saveAndFlush(customer);
    }

    @Test
    void getByCustomerAndLikingDateBetween(){
        //ARRANGE
        Customer customer = customerRepository.findByUsername("brr").get();

        //ACT
        List<Liking> likings = likingRepository.getByCustomerAndLikingDateBetween(customer,LocalDateTime.now().minusDays(2),LocalDateTime.now());

        //ASSERT
        Assertions.assertEquals(2,likings.size());

    }
}
