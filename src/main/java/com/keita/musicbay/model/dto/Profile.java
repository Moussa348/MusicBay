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
    private Integer nbrOfLikedMusic,nbrOfSharedMusic,nbrOfPurchasedMusic;

    public Profile(){}

    @Builder
    public Profile(User user, List<LikedMusic> likedMusics, List<SharedMusic> sharedMusics, List<PurchasedMusic> purchasedMusics){
        this.username = user.getUserName();
        this.email = user.getEmail();
        this.city = user.getCity();
        this.nbrOfFollower = user.getUsers().stream().filter(User::isActive).count();
        this.nbrOfLikedMusic = likedMusics.size();
        this.nbrOfSharedMusic = sharedMusics.size();
        this.nbrOfPurchasedMusic = purchasedMusics.size();
    }
}
