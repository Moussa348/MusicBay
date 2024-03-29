package com.keita.musicbay.repository;

import com.keita.musicbay.model.entity.Customer;
import com.keita.musicbay.model.entity.SubscribeTo;
import com.keita.musicbay.model.entity.Subscriber;
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
public class SubscribeToRepositoryTest {

    @Autowired
    SubscribeToRepository subscribeToRepository;

    @Autowired
    CustomerRepository customerRepository;

    @BeforeEach
    void insert() {
        customerRepository.save(Customer.builder().username("brr").build());

        List<SubscribeTo> subscribersTo = Arrays.asList(
                SubscribeTo.builder().user(customerRepository.findByUsername("brr").get()).build(),
                SubscribeTo.builder().user(customerRepository.findByUsername("brr").get()).build()
        );
        subscribeToRepository.saveAll(subscribersTo);
    }

    @Test
    void getAllByUserUsername(){
        //ARRANGE
        String username = "brr";

        //ACT
        List<SubscribeTo> subscribeTos = subscribeToRepository.getAllByUserUsername(username, PageRequest.of(0,3, Sort.by("date").ascending()));
        //ASSERT
        assertEquals(2,subscribeTos.size());
    }

    @Test
    void countAllByUserUsername(){
        //ACT
        Double nbrOfSub = subscribeToRepository.countAllByUserUsername("brr");

        //ASSERT
        assertEquals(2,nbrOfSub);
    }
}
