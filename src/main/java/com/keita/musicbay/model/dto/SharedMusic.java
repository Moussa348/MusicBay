package com.keita.musicbay.model.dto;

import com.keita.musicbay.model.Sharing;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class SharedMusic implements Serializable {
    private String title,description,tags,sharingMsg;
    private Integer nbrOfLike,nbrOfShare,nbrOfPlay,nbrOfComment;
    private LocalDateTime date,sharingDate;
    private Float price;

    public SharedMusic(){}

    public SharedMusic(Sharing sharing){
        this.title = sharing.getMusic().getTitle();
        this.description = sharing.getMusic().getDescription();
        this.tags = sharing.getMusic().getTags();
        this.date = sharing.getMusic().getDate();
        this.nbrOfLike = sharing.getMusic().getNbrOfLike();
        this.nbrOfShare = sharing.getMusic().getNbrOfShare();
        this.nbrOfPlay = sharing.getMusic().getNbrOfPlay();
        this.price = sharing.getMusic().getPrice();
        this.nbrOfComment = sharing.getMusic().getComments().size();
        this.sharingDate = sharing.getSharingDate();
        this.sharingMsg = sharing.getSharingMsg();

    }
}
