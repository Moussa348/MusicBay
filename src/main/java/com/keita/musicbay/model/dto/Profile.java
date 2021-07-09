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

    public Profile(Customer customer){
        this.uuid = customer.getUuid();
        this.username = customer.getUsername();
        this.email = customer.getEmail();
        this.biography =customer.getBiography();
        this.city = customer.getCity();
        this.nbrOfSubscriber = customer.getSubscribers().size();
        this.nbrOfSubscribeTo = customer.getSubscribeTos().size();

    }
}
