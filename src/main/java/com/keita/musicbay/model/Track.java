package com.keita.musicbay.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.List;

@Entity
@Data
@ToString
@EqualsAndHashCode(callSuper = true)
public class Track extends Music implements Serializable {

    @OneToMany(mappedBy = "track")
    private List<File> files;
    private Float bpm;

    public Track(){}

    @Builder
    public Track(String title, String description, String tags, Integer nbrOfLike,
                 Integer nbrOfShare, Integer nbrOfPLay, Integer nbrOfPurchase, Float price, Float bpm) {
        super(title, description, tags, nbrOfLike, nbrOfShare, nbrOfPLay, nbrOfPurchase,price);
        this.bpm = bpm;
    }
}
