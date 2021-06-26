package com.keita.musicbay.model.dto;

import com.keita.musicbay.model.LikedMusic;
import com.keita.musicbay.model.PurchasedMusic;
import com.keita.musicbay.model.SharedMusic;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class MusicDTO implements Serializable {
    private String title,description,tags,sharingMsg;
    private LocalDateTime date,likingDate,sharingDate,purchasingDate;
    private Integer nbrOfLike,nbrOfShare,nbrOfPLay,nbrOfComment;
    private Float price;

    public MusicDTO(){}

    public MusicDTO(LikedMusic likedMusic){
        this.title = likedMusic.getTitle();
        this.description = likedMusic.getDescription();
        this.tags = likedMusic.getTags();
        this.date = likedMusic.getDate();
        this.nbrOfLike = likedMusic.getNbrOfLike();
        this.nbrOfShare = likedMusic.getNbrOfShare();
        this.nbrOfPLay = likedMusic.getNbrOfPLay();
        this.price = likedMusic.getPrice();
        this.nbrOfComment = likedMusic.getComments().size();
        this.likingDate = likedMusic.getLikingDate();

    }

    public MusicDTO(SharedMusic sharedMusic){
        this.title = sharedMusic.getTitle();
        this.description = sharedMusic.getDescription();
        this.tags = sharedMusic.getTags();
        this.date = sharedMusic.getDate();
        this.nbrOfLike = sharedMusic.getNbrOfLike();
        this.nbrOfShare = sharedMusic.getNbrOfShare();
        this.nbrOfPLay = sharedMusic.getNbrOfPLay();
        this.price = sharedMusic.getPrice();
        this.nbrOfComment = sharedMusic.getComments().size();
        this.sharingDate = sharedMusic.getSharingDate();
        this.sharingMsg = sharedMusic.getSharingMsg();
    }

    public MusicDTO(PurchasedMusic purchasedMusic){
        this.title = purchasedMusic.getTitle();
        this.description = purchasedMusic.getDescription();
        this.tags = purchasedMusic.getTags();
        this.date = purchasedMusic.getDate();
        this.nbrOfLike = purchasedMusic.getNbrOfLike();
        this.nbrOfShare = purchasedMusic.getNbrOfShare();
        this.nbrOfPLay = purchasedMusic.getNbrOfPLay();
        this.price = purchasedMusic.getPrice();
        this.nbrOfComment = purchasedMusic.getComments().size();
        this.purchasingDate = purchasedMusic.getPurchasingDate();
    }
}
