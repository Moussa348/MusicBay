package com.keita.musicbay.model.entity;

import com.keita.musicbay.model.enums.NotificationEvent;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Data
public class Notification implements Serializable {
    @Id
    @GeneratedValue
    private Long id;
    private NotificationEvent notificationEvent;
    private LocalDateTime date;
    private boolean seen;

    @ManyToOne
    private User user;

    public Notification(){}

    public Notification(NotificationEvent notificationEvent, User user) {
        this.notificationEvent = notificationEvent;
        this.date = LocalDateTime.now();
        this.seen = false;
        this.user = user;
    }
}
