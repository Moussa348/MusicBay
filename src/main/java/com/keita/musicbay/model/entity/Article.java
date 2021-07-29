package com.keita.musicbay.model.entity;

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
    private String owner,purchasedBy;
    private PriceType priceType;
    private Float price;
    private boolean articleHasBeenSent;

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
        this.owner = music.getProducer().getUsername();
        this.purchasedBy = this.transaction.getCustomer().getUsername();
        this.articleHasBeenSent = false;
    }

}
