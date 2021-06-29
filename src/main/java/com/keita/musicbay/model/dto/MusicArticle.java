package com.keita.musicbay.model.dto;

import com.keita.musicbay.model.Music;
import lombok.Data;

import java.io.Serializable;

@Data
public class MusicArticle implements Serializable {
    private String title, timeLength;
    private Float price;

    public MusicArticle(Music music){
        this.title = music.getTitle();
        this.timeLength = music.getTimeLength();
        this.price = music.getPrice();
    }
}
