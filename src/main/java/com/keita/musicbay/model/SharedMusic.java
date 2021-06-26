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
public class SharedMusic extends Music implements Serializable {

    @ManyToOne
    private Customer customer;
    private LocalDate sharingDate;
    private String sharingMsg;

    public SharedMusic() { }

    public SharedMusic(String title, String description, String tags, Integer nbrOfLike, Integer nbrOfShare, LocalDate sharingDate,
                       Integer nbrOfPLay, Float price, List<Comment> comments, Customer customer, String sharingMsg) {
        super(title, description, tags, nbrOfLike, nbrOfShare, nbrOfPLay, price, comments);
        this.customer = customer;
        this.sharingDate = sharingDate;
        this.sharingMsg = sharingMsg;
    }
}
