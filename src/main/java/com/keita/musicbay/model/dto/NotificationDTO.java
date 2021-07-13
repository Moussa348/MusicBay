package com.keita.musicbay.model.dto;

import com.keita.musicbay.model.entity.Notification;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class NotificationDTO implements Serializable {
    private Long id;
    private String event,username;
    private LocalDateTime date;

    public NotificationDTO(){}

    public NotificationDTO(Notification notification){
        this.id = notification.getId();
        this.event = notification.getEvent();
        this.username = notification.getUser().getUsername();
        this.date = notification.getDate();
    }
}
