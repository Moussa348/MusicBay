package com.keita.musicbay.model.dto;

import com.keita.musicbay.model.*;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class Profile implements Serializable {
    private String username,email,city;
    private Long nbrOfFollower;
    private Integer nbrOfSubscriber,nbrOfSubscribeTo,nbrOfLikedMusic,nbrOfSharedMusic,nbrOfPurchasedMusic;

    public Profile(){}

    @Builder
    public Profile(User user, List<Liking> likings, List<Sharing> sharings, List<Purchasing> purchasings){
        this.username = user.getUserName();
        this.email = user.getEmail();
        this.city = user.getCity();
        this.nbrOfSubscriber = user.getSubscribers().size();
        this.nbrOfSubscribeTo = user.getSubscribeTos().size();
        this.nbrOfFollower = (user.getUsers() != null) ?user.getUsers().stream().filter(User::isActive).count():0;
        this.nbrOfLikedMusic = likings.size();
        this.nbrOfSharedMusic = sharings.size();
        this.nbrOfPurchasedMusic = purchasings.size();
    }
}
