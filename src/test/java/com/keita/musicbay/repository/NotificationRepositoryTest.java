package com.keita.musicbay.repository;

import com.keita.musicbay.model.entity.Customer;
import com.keita.musicbay.model.entity.Notification;
import com.keita.musicbay.model.entity.Purchasing;
import com.keita.musicbay.model.entity.User;
import com.keita.musicbay.model.enums.NotificationEvent;
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
                new Notification(NotificationEvent.LIKING,customerRepository.findByUsername(customer.getUsername()).get()),
                new Notification(NotificationEvent.LIKING,customerRepository.findByUsername(customer.getUsername()).get()),
                new Notification(NotificationEvent.LIKING,customerRepository.findByUsername(customer.getUsername()).get())
        );

        notifications.get(0).setSeen(true);


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

    @Test
    void getAllByUserAndSeenFalse(){
        //ARRANGE
        User user = customerRepository.findByUsername("brr").get();
        //ACT
        List<Notification> notifications = notificationRepository.getAllByUserAndSeenFalse(user, PageRequest.of(0,3, Sort.by("date").ascending()));

        //ASSERT
        assertEquals(2,notifications.size());
    }
}
