package com.keita.musicbay.repository;

import com.keita.musicbay.model.entity.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
public class SubscriberRepositoryTest {

    @Autowired
    SubscriberRepository subscriberRepository;

    @Autowired
    CustomerRepository customerRepository;

    @BeforeEach
    void insert() {
        customerRepository.save(Customer.builder().username("brr").build());
        customerRepository.save(Customer.builder().username("bigBrr").build());

        List<Subscriber> subscribers = Arrays.asList(
                Subscriber.builder().user(customerRepository.findByUsername("brr").get()).build(),
                Subscriber.builder().user(customerRepository.findByUsername("brr").get()).build(),
                Subscriber.builder().user(customerRepository.findByUsername("bigBrr").get()).build(),
                Subscriber.builder().user(customerRepository.findByUsername("bigBrr").get()).build(),
                Subscriber.builder().user(customerRepository.findByUsername("bigBrr").get()).build()
        );
        subscriberRepository.saveAll(subscribers);
    }


    @Test
    void getAllByUserUsername(){
        //ARRANGE
        String username = "brr";

        //ACT
        List<Subscriber> subscribers = subscriberRepository.getAllByUserUsername(username, PageRequest.of(0,3, Sort.by("date").ascending()));
        //ASSERT
        assertEquals(2,subscribers.size());
    }

    @Test
    void countAllByUserUsername(){
        //ACT
        Double nbrOfSub = subscriberRepository.countAllByUserUsername("brr");

        //ASSERT
        assertEquals(2,nbrOfSub);
    }
}
