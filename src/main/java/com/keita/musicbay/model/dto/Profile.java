package com.keita.musicbay.model.dto;

import com.keita.musicbay.model.*;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Data
public class Profile implements Serializable {
    private UUID uuid;
    private String username,email,city,biography;
    private Integer nbrOfSubscriber,nbrOfSubscribeTo;

    public Profile(){}

    public Profile(User user){
        this.uuid = user.getUuid();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.biography =user.getBiography();
        this.city = user.getCity();
        this.nbrOfSubscriber = user.getSubscribers().size();
        this.nbrOfSubscribeTo = user.getSubscribeTos().size();

    }
}
