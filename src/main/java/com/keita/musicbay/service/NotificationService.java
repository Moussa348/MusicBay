package com.keita.musicbay.service;

import com.keita.musicbay.model.dto.RecentNotification;
import com.keita.musicbay.model.entity.Notification;
import com.keita.musicbay.model.entity.User;
import com.keita.musicbay.model.enums.NotificationEvent;
import com.keita.musicbay.repository.NotificationRepository;
import com.keita.musicbay.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private UserRepository userRepository;

    //TODO : link to the publication

    public void saveNotification(String triggeredBy,NotificationEvent notificationEvent, List<String> usernames){
        List<User> users = usernames.stream().map(username -> userRepository.findByUsername(username).get()).collect(Collectors.toList());
        users.forEach(user -> notificationRepository.save(new Notification(notificationEvent,user,triggeredBy)));
    }


    public void notificationSeen(Long id){
        Notification notification = notificationRepository.getById(id);

        notification.setSeen(true);

        notificationRepository.save(notification);
    }

    public List<RecentNotification> getRecentNotifications(String username, Integer noPage){

        return notificationRepository.getAllByUserUsernameAndSeenFalse(username, PageRequest.of(noPage,10, Sort.by("date").ascending()))
                .stream().map(RecentNotification::new).collect(Collectors.toList());
    }
}
