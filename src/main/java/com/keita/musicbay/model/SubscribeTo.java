package com.keita.musicbay.model;

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
public class SubscribeTo implements Serializable {

    @Id
    @GeneratedValue
    private Long id;
    private LocalDateTime date;
    private String username;

    @ManyToOne
    private User user;

    public SubscribeTo(){}

    @Builder
    public SubscribeTo(String username,User user) {
        this.date = LocalDateTime.now();
        this.username = username;
        this.user = user;
    }
}
