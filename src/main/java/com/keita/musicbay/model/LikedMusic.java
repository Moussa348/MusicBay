package com.keita.musicbay.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class LikedMusic extends Music implements Serializable {

    @ManyToOne
    private Customer customer;

    public LikedMusic() { }

    public LikedMusic(String title, String description, String tags, Integer nbrOfLike, Integer nbrOfShare,
                      Integer nbrOfPLay, Float price, List<Comment> comments, Customer customer) {
        super(title, description, tags, nbrOfLike, nbrOfShare, nbrOfPLay, price, comments);
        this.customer = customer;
    }
}