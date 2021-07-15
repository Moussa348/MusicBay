package com.keita.musicbay.repository;

import com.keita.musicbay.model.entity.Customer;
import com.keita.musicbay.model.entity.Liking;
import com.keita.musicbay.model.entity.Sharing;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
    void getAllByCustomerUsername(){
        //ARRANGE
        String username = "brr";

        //ACT
        List<Sharing> sharings = sharingRepository.getAllByCustomerUsername(username, PageRequest.of(0,3, Sort.by("sharingDate").ascending()));

        //ASSERT
        assertEquals(3,sharings.size());
    }
}
