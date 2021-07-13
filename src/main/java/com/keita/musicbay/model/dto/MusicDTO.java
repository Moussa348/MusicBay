package com.keita.musicbay.model.dto;

import com.keita.musicbay.model.entity.Music;
import com.keita.musicbay.model.entity.Track;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class MusicDTO implements Serializable {
    private String title,description,tags,creator,timeLength,type;
    private LocalDateTime date;
    private Integer nbrOfLike,nbrOfShare,nbrOfPlay,nbrOfPurchase,nbrOfComment;
    private Float basicPrice,exclusivePrice;

    public MusicDTO(){}

    public MusicDTO(Music music){
        this.title = music.getTitle();
        this.description = music.getDescription();
        this.tags = music.getTags();
        this.timeLength = music.getTimeLength();
        this.date = music.getDate();
        this.nbrOfLike = music.getNbrOfLike();
        this.nbrOfShare = music.getNbrOfShare();
        this.nbrOfPlay = music.getNbrOfPlay();
        this.basicPrice = music.getBasicPrice();
        this.exclusivePrice = music.getExclusivePrice();
        this.nbrOfComment = music.getComments().size();
        this.creator = "bombay";
        this.type = music instanceof Track ? "TRACK":"MIXTAPE";
    }
}
