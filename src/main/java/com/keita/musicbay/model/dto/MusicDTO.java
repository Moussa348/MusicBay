package com.keita.musicbay.model.dto;

import com.keita.musicbay.model.Music;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class MusicDTO implements Serializable {
    private String title,description,tags,sharingMsg;
    private LocalDateTime date,likingDate,sharingDate,purchasingDate;
    private Integer nbrOfLike,nbrOfShare,nbrOfPlay,nbrOfPurchase,nbrOfComment;
    private Float price;

    public MusicDTO(){}

    public MusicDTO(Music music){
        this.title = music.getTitle();
        this.description = music.getDescription();
        this.tags = music.getTags();
        this.date = music.getDate();
        this.nbrOfLike = music.getNbrOfLike();
        this.nbrOfShare = music.getNbrOfShare();
        this.nbrOfPlay = music.getNbrOfPlay();
        this.price = music.getPrice();
        this.nbrOfComment = music.getComments().size();
    }
}