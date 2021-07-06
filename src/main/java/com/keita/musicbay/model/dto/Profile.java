package com.keita.musicbay.model.dto;

import com.keita.musicbay.model.*;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class Profile implements Serializable {
    private String username,email,city,biography;
    private Integer nbrOfSubscriber,nbrOfSubscribeTo;

    public Profile(){}

    public Profile(Customer customer){
        this.username = customer.getUserName();
        this.email = customer.getEmail();
        this.biography = customer.getBiography();
        this.city = customer.getCity();
        this.nbrOfSubscriber = customer.getSubscribers().size();
        this.nbrOfSubscribeTo = customer.getSubscribeTos().size();

    }
}
