package com.keita.musicbay.model.dto;

import com.keita.musicbay.model.entity.Article;
import com.keita.musicbay.model.entity.Music;
import com.keita.musicbay.model.enums.PriceType;
import lombok.Data;

import java.io.Serializable;

@Data
public class MusicArticle implements Serializable {
    private String title, timeLength,fileType;
    private PriceType priceType;
    private Float price;

    public MusicArticle(Music music){
        this.title = music.getTitle();
        this.timeLength = music.getTimeLength();
    }

    public MusicArticle(Article article){
        this.title = article.getMusic().getTitle();
        this.timeLength = article.getMusic().getTimeLength();
        this.priceType = article.getPriceType();
        this.price = article.getPrice();
    }
}
