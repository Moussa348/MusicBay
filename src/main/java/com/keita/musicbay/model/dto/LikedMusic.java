package com.keita.musicbay.model.dto;

import com.keita.musicbay.model.Liking;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class LikedMusic implements Serializable {
    private String title,description,tags;
    private Integer nbrOfLike,nbrOfShare,nbrOfPlay,nbrOfComment;
    private LocalDateTime date,likingDate;
    private Float price;

    public LikedMusic(){ }

    public LikedMusic(Liking liking){
        this.title = liking.getMusic().getTitle();
        this.description = liking.getMusic().getDescription();
        this.tags = liking.getMusic().getTags();
        this.date = liking.getMusic().getDate();
        this.nbrOfLike = liking.getMusic().getNbrOfLike();
        this.nbrOfShare = liking.getMusic().getNbrOfShare();
        this.nbrOfPlay = liking.getMusic().getNbrOfPlay();
        this.price = liking.getMusic().getPrice();
        this.nbrOfComment = liking.getMusic().getComments().size();
        this.likingDate = liking.getLikingDate();
    }
}
