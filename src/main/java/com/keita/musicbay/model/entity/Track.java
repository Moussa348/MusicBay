package com.keita.musicbay.model.entity;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@ToString
@EqualsAndHashCode(callSuper = true)
public class Track extends Music implements Serializable {

    @OneToMany(mappedBy = "track")
    private List<File> files = new ArrayList<>();
    private Float bpm;

    public Track(){}

    @Builder
    public Track(String title, String description, String tags, Integer nbrOfLike,String timeLength,Producer producer,
                 Integer nbrOfShare, Integer nbrOfPLay, Integer nbrOfPurchase,Float basicPrice,Float exclusivePrice, Float bpm) {
        super(title, timeLength,description, tags, nbrOfLike,producer, nbrOfShare, nbrOfPLay, nbrOfPurchase,basicPrice,exclusivePrice);
        this.bpm = bpm;
    }
}
