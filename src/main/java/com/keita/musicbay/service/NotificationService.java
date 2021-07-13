package com.keita.musicbay.service;

import com.keita.musicbay.model.dto.RecentNotification;
import com.keita.musicbay.model.entity.Notification;
import com.keita.musicbay.model.entity.User;
import com.keita.musicbay.model.enums.NotificationEvent;
import com.keita.musicbay.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    public void saveNotification(NotificationEvent notificationEvent, User user){
        notificationRepository.save(new Notification(notificationEvent,user));
    }


    public void notificationSeen(Long id){
        Notification notification = notificationRepository.getById(id);

        notification.setSeen(true);

        notificationRepository.save(notification);
    }

    public List<RecentNotification> getRecentNotifications(String username, String date, Integer nbrOfDays){
        LocalDateTime todayDate = LocalDateTime.parse(date,DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

        return notificationRepository.getByUserUsernameAndDateBetween(username,todayDate.minusDays(nbrOfDays),todayDate)
                .stream().map(RecentNotification::new).collect(Collectors.toList());
    }
}
