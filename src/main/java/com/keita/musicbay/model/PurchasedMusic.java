package com.keita.musicbay.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class PurchasedMusic extends Music implements Serializable {

    @ManyToOne
    private Customer customer;
    private LocalDateTime purchasingDate;

    public PurchasedMusic() { }

    @Builder
    public PurchasedMusic(String title, String description, String tags, Integer nbrOfLike, Integer nbrOfShare,
                          LocalDateTime purchasingDate, Integer nbrOfPLay, Float price, Customer customer) {
        super(title, description, tags, nbrOfLike, nbrOfShare, nbrOfPLay, price);
        this.customer = customer;
        this.purchasingDate = purchasingDate;
    }
}
