package com.keita.musicbay.model;

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

    @ManyToOne
    private Transaction transaction;

    @OneToOne
    private Music music;

    public Article(){}

    @Builder
    public Article(Long id, PriceType priceType, Transaction transaction, Music music) {
        this.id = id;
        this.priceType = priceType;
        this.transaction = transaction;
        this.music = music;
    }
}
