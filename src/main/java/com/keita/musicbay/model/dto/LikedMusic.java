package com.keita.musicbay.model.dto;

import com.keita.musicbay.model.Liking;
import com.keita.musicbay.model.Music;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class LikedMusic implements Serializable {
    private String likedBy;
    private LocalDateTime likingDate;
    private MusicDTO music;

    public LikedMusic(){ }

    public LikedMusic(Liking liking){
        this.likedBy = liking.getCustomer().getUsername();
        this.likingDate = liking.getLikingDate();
        this.music = new MusicDTO(liking.getMusic());
    }
}
