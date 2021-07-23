package com.keita.musicbay.model.dto;

import com.keita.musicbay.model.entity.File;
import com.keita.musicbay.model.entity.Mp3File;
import com.keita.musicbay.model.entity.Music;
import com.keita.musicbay.model.entity.Track;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class MusicDTO implements Serializable {
    private String title,description,tags,creator,timeLength,type;
    private LocalDateTime date;
    private Integer nbrOfLike,nbrOfShare,nbrOfPlay,nbrOfPurchase,nbrOfComment;
    private Float basicPrice,exclusivePrice;
    private List<String> fileNames;

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
        this.nbrOfPurchase = music.getNbrOfPurchase();
        this.basicPrice = music.getBasicPrice();
        this.exclusivePrice = music.getExclusivePrice();
        this.nbrOfComment = music.getComments().size();
        this.creator = "bombay";
        this.type = music instanceof Track ? "TRACK":"MIXTAPE";
        this.fileNames = music instanceof Track ?
                ((Track) music).getFiles().stream().filter(file -> file instanceof Mp3File).map(File::getFileName).collect(Collectors.toList())
                : Collections.emptyList();
    }
}
