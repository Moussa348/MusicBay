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

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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
        User user = Customer.builder().username("bigBrr").build();

        //ACT
        notificationService.saveNotification(notificationEvent,user);
    }

    @Test
    void notificationSeen(){
        //ARRANGE
        Long id = 1L;
        Notification notification = new Notification(NotificationEvent.LIKING,Customer.builder().build());
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
                new Notification(NotificationEvent.LIKING,customer),
                new Notification(NotificationEvent.LIKING,customer),
                new Notification(NotificationEvent.LIKING,customer)
        );

        when(userRepository.findByUsername(customer.getUsername())).thenReturn(Optional.of(customer));

        when(notificationRepository.getAllByUserAndSeenFalse(customer, PageRequest.of(noPage,10, Sort.by("date").ascending()))).thenReturn(notifications);
        //ACT
        List<RecentNotification> recentNotifications = notificationService.getRecentNotifications(customer.getUsername(),noPage);

        //ASSERT
        assertEquals(3,recentNotifications.size());
    }



}
