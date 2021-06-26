package com.keita.musicbay.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class PurchasedMusic extends Music implements Serializable {

    @ManyToOne
    private Customer customer;
    private LocalDate purchaseDate;

    public PurchasedMusic() { }

    public PurchasedMusic(String title, String description, String tags, Integer nbrOfLike, Integer nbrOfShare,
                          LocalDate purchaseDate, Integer nbrOfPLay, Float price, List<Comment> comments, Customer customer) {
        super(title, description, tags, nbrOfLike, nbrOfShare, nbrOfPLay, price, comments);
        this.customer = customer;
        this.purchaseDate = purchaseDate;
    }
}
