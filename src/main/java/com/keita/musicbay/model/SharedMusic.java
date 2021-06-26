package com.keita.musicbay.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class SharedMusic extends Music implements Serializable {

    @ManyToOne
    private Customer customer;
    private LocalDateTime sharingDate;
    private String sharingMsg;

    public SharedMusic() { }

    @Builder
    public SharedMusic(String title, String description, String tags, Integer nbrOfLike, Integer nbrOfShare,
                       LocalDateTime sharingDate, Integer nbrOfPLay, Float price, Customer customer, String sharingMsg) {
        super(title, description, tags, nbrOfLike, nbrOfShare, nbrOfPLay, price);
        this.customer = customer;
        this.sharingDate = sharingDate;
        this.sharingMsg = sharingMsg;
    }
}
