package com.keita.musicbay.model.dto;

import com.keita.musicbay.model.Purchasing;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class PurchasedMusic implements Serializable {
    private String title,description,tags;
    private Integer nbrOfLike,nbrOfShare,nbrOfPlay,nbrOfComment;
    private LocalDateTime date,purchasingDate;
    private Float price;

    public PurchasedMusic(){}

    public PurchasedMusic(Purchasing purchasing){
        this.title = purchasing.getMusic().getTitle();
        this.description = purchasing.getMusic().getDescription();
        this.tags = purchasing.getMusic().getTags();
        this.date = purchasing.getMusic().getDate();
        this.nbrOfLike = purchasing.getMusic().getNbrOfLike();
        this.nbrOfShare = purchasing.getMusic().getNbrOfShare();
        this.nbrOfPlay = purchasing.getMusic().getNbrOfPlay();
        this.price = purchasing.getMusic().getPrice();
        this.nbrOfComment = purchasing.getMusic().getComments().size();
        this.purchasingDate = purchasing.getPurchasingDate();
    }
}
