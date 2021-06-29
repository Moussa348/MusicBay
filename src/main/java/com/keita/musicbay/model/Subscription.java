package com.keita.musicbay.model;

import com.keita.musicbay.model.enums.SubscriptionType;
import lombok.Builder;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Data
public class Subscription implements Serializable {

    @Id
    @GeneratedValue
    private Long id;
    private LocalDateTime date;
    private String username;
    private SubscriptionType subscriptionType;

    @ManyToOne
    private User user;

    public Subscription(){}

    @Builder
    public Subscription(Long id, LocalDateTime date, String username, SubscriptionType subscriptionType, User user) {
        this.id = id;
        this.date = date;
        this.username = username;
        this.subscriptionType = subscriptionType;
        this.user = user;
    }
}
