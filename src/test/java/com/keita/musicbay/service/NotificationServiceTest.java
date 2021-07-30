package com.keita.musicbay.service;

import com.keita.musicbay.model.dto.RecentNotification;
import com.keita.musicbay.model.entity.Customer;
import com.keita.musicbay.model.entity.Notification;
import com.keita.musicbay.model.entity.User;
import com.keita.musicbay.model.enums.NotificationEvent;
import com.keita.musicbay.repository.NotificationRepository;
import com.keita.musicbay.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class NotificationServiceTest {

    @Mock
    NotificationRepository notificationRepository;

    @Mock
    UserRepository userRepository;

    @InjectMocks
    NotificationService notificationService;

    @Test
    void saveNotification(){
        //ARRANGE
        NotificationEvent notificationEvent = NotificationEvent.PURCHASING;
        String triggeredBy = "bombay";

        List<User> users = Arrays.asList(
                Customer.builder().username("bayDrip").build()
        );
        users.stream().map(User::getUsername).forEach(username -> when(userRepository.findByUsername(username)).thenReturn(Optional.of(users.get(0))));

        //ACT
        notificationService.saveNotification(triggeredBy,notificationEvent,users.stream().map(User::getUsername).collect(Collectors.toList()));
    }

    @Test
    void notificationSeen(){
        //ARRANGE
        Long id = 1L;
        Notification notification = new Notification(NotificationEvent.LIKING,Customer.builder().build(),"bombay");
        when(notificationRepository.getById(id)).thenReturn(notification);

        //ACT
        notificationService.notificationSeen(id);
    }

    @Test
    void getRecentNotifications(){
        //ARRANGE
        Customer customer = Customer.builder().username("brr").build();
        int noPage = 0;

        List<Notification> notifications = Arrays.asList(
                new Notification(NotificationEvent.LIKING,customer,"bombay"),
                new Notification(NotificationEvent.LIKING,customer,"bombay"),
                new Notification(NotificationEvent.LIKING,customer,"bombay")
        );


        when(notificationRepository.getAllByUserUsernameAndSeenFalse(customer.getUsername(), PageRequest.of(noPage,10, Sort.by("date").ascending()))).thenReturn(notifications);
        //ACT
        List<RecentNotification> recentNotifications = notificationService.getRecentNotifications(customer.getUsername(),noPage);

        //ASSERT
        assertEquals(3,recentNotifications.size());
    }

    @Test
    void getNbrOfPage(){
        //ARRANGE
        String username = "hope";
        when(notificationRepository.countAllByUserUsername(username)).thenReturn(4.2);

        //ACT
        Integer nbrOfPage = notificationService.getNbrOfPage(username);

        //ASSERT
        assertEquals(1,nbrOfPage);
    }



}
