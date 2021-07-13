package com.keita.musicbay.repository;

import com.keita.musicbay.model.entity.Conversation;
import com.keita.musicbay.model.entity.Customer;
import com.keita.musicbay.model.entity.Message;
import com.keita.musicbay.model.entity.Subscriber;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
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

        List<Subscriber> subscribers = Arrays.asList(
                Subscriber.builder().user(customerRepository.findByUsername("brr").get()).build(),
                Subscriber.builder().user(customerRepository.findByUsername("brr").get()).build()
        );
        subscriberRepository.saveAll(subscribers);
    }

    @Test
    void getByUserUsername(){
        //ARRANGE
        String username = "brr";

        //ACT
        List<Subscriber> subscribers = subscriberRepository.getByUserUsername(username);

        //ASSERT
        assertEquals(2,subscribers.size());
    }
}
