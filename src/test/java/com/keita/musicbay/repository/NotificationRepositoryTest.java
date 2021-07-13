package com.keita.musicbay.repository;

import com.keita.musicbay.model.entity.Customer;
import com.keita.musicbay.model.entity.Notification;
import com.keita.musicbay.model.entity.Purchasing;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
public class NotificationRepositoryTest {

    @Autowired
    NotificationRepository notificationRepository;

    @Autowired
    CustomerRepository customerRepository;


    @BeforeEach
    void insert(){
        Customer customer = Customer.builder().username("brr").build();
        customerRepository.save(customer);
        List<Notification> notifications = Arrays.asList(
                new Notification("",customerRepository.findByUsername(customer.getUsername()).get()),
                new Notification("",customerRepository.findByUsername(customer.getUsername()).get()),
                new Notification("",customerRepository.findByUsername(customer.getUsername()).get())
        );


        notificationRepository.saveAll(notifications);

    }

    @Test
    void getByUserUsernameAndDateBetween(){
        //ARRANGE
        String username = "brr";
        //ACT
        List<Notification> notifications = notificationRepository.getByUserUsernameAndDateBetween(username,LocalDateTime.now().minusDays(2),LocalDateTime.now());

        //ASSERT
        assertEquals(3,notifications.size());
    }
}
