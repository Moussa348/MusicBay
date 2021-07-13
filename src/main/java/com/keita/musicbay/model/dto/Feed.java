package com.keita.musicbay.model.dto;

import com.keita.musicbay.model.entity.Liking;
import com.keita.musicbay.model.entity.Purchasing;
import com.keita.musicbay.model.entity.Sharing;
import com.keita.musicbay.model.entity.User;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class Feed implements Serializable {
    private List<LikedMusic> likedMusics;
    private List<SharedMusic> sharedMusics;
    private List<PurchasedMusic> purchasedMusics;
    private List<Profile> profiles;

    public Feed(){}

    public Feed(List<Liking> likings, List<Sharing> sharings, List<Purchasing> purchasings, List<User> users){
        this.likedMusics = likings.stream().map(LikedMusic::new).collect(Collectors.toList());
        this.sharedMusics = sharings.stream().map(SharedMusic::new).collect(Collectors.toList());
        this.purchasedMusics = purchasings.stream().map(PurchasedMusic::new).collect(Collectors.toList());
        this.profiles = users.stream().map(Profile::new).collect(Collectors.toList());
    }
}
