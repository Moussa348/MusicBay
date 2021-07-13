package com.keita.musicbay.service;

import com.keita.musicbay.model.dto.NotificationDTO;
import com.keita.musicbay.model.entity.Customer;
import com.keita.musicbay.model.entity.Notification;
import com.keita.musicbay.model.entity.User;
import com.keita.musicbay.repository.NotificationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class NotificationServiceTest {

    @Mock
    NotificationRepository notificationRepository;

    @InjectMocks
    NotificationService notificationService;

    @Test
    void saveNotification(){
        //ARRANGE
        String event = "sharing";
        User user = Customer.builder().username("bigBrr").build();

        //ACT
        notificationService.saveNotification(event,user);
    }

    @Test
    void notificationSeen(){
        //ARRANGE
        Long id = 1L;
        Notification notification = new Notification("liking",Customer.builder().build());
        when(notificationRepository.getById(id)).thenReturn(notification);

        //ACT
        notificationService.notificationSeen(id);
    }

    @Test
    void getRecentNotification(){
        //ARRANGE
        Customer customer = Customer.builder().username("brr").build();
        LocalDateTime date = LocalDateTime.parse("2021-07-12 07:27", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        int nbrOfDays = 2;

        List<Notification> notifications = Arrays.asList(
                new Notification("",customer),
                new Notification("",customer),
                new Notification("",customer)
        );

        when(notificationRepository.getByUserUsernameAndDateBetween(customer.getUsername(),date.minusDays(nbrOfDays),date)).thenReturn(notifications);
        //ACT
        List<NotificationDTO> recentNotifications = notificationService.getRecentNotification(customer.getUsername(),"2021-07-12 07:27",nbrOfDays);

        //ASSERT
        assertEquals(3,recentNotifications.size());
    }



}
