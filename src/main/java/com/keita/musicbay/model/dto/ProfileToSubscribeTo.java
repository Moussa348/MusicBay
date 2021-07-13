package com.keita.musicbay.model.dto;

import com.keita.musicbay.model.entity.User;
import lombok.Data;

import java.io.Serializable;

@Data
public class ProfileToSubscribeTo implements Serializable {
    private String username;
    private Integer nbrOfSubscriber,nbrOfSubscribeTo;

    public ProfileToSubscribeTo(){}

    public ProfileToSubscribeTo(User user){
        this.username = user.getUsername();
        this.nbrOfSubscriber = user.getSubscribers().size();
        this.nbrOfSubscribeTo = user.getSubscribeTos().size();
    }
}
