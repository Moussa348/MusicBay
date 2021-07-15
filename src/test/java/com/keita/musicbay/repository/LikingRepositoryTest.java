package com.keita.musicbay.repository;

import com.keita.musicbay.model.entity.Customer;
import com.keita.musicbay.model.entity.Liking;
import lombok.extern.java.Log;
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
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
@Log
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
    void getAllByCustomerUsername(){
        //ARRANGE
        String username = "brr";

        //ACT
        List<Liking> likings = likingRepository.getAllByCustomerUsername(username, PageRequest.of(0,3, Sort.by("likingDate").ascending()));
        //ASSERT
        assertEquals(3,likings.size());
    }

}
