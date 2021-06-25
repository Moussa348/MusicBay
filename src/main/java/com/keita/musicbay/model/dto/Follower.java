package com.keita.musicbay.model.dto;

import com.keita.musicbay.model.User;
import lombok.Data;

import java.io.Serializable;

@Data
public class Follower implements Serializable {
    private String username,email,city;
    private Long nbrOfFollower;

    public Follower(){}

    public Follower(User user){
        this.username = user.getUserName();
        this.email = user.getEmail();
        this.city = user.getCity();
        this.nbrOfFollower = user.getUsers().stream().filter(User::isActive).count();
    }
}
