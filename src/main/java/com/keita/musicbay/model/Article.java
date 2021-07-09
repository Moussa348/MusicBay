package com.keita.musicbay.model;

import com.keita.musicbay.model.dto.MusicArticle;
import com.keita.musicbay.model.enums.PriceType;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
public class Article implements Serializable {

    @Id
    @GeneratedValue
    private Long id;
    private PriceType priceType;
    private Float price;

    @ManyToOne
    private Transaction transaction;

    @OneToOne
    private Music music;

    public Article(){}

    @Builder
    public Article(PriceType priceType, Transaction transaction, Music music) {
        this.priceType = priceType;
        this.transaction = transaction;
        this.music = music;
        this.price = priceType.equals(PriceType.BASIC) ? music.getBasicPrice():music.getExclusivePrice();
    }

}
