package com.keita.musicbay.model.dto;

import com.keita.musicbay.model.entity.Notification;
import com.keita.musicbay.model.enums.NotificationEvent;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class RecentNotification implements Serializable {
    private Long id;
    private String username;
    private NotificationEvent notificationEvent;
    private LocalDateTime date;

    public RecentNotification(){}

    public RecentNotification(Notification notification){
        this.id = notification.getId();
        this.notificationEvent = notification.getNotificationEvent();
        this.username = notification.getUser().getUsername();
        this.date = notification.getDate();
    }
}
