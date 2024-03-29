package com.keita.musicbay.model.entity;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class MixTape extends Music implements Serializable {

    @OneToMany
    private List<Track> tracks;

    public MixTape(){}

    @Builder
    public MixTape(String title, String description, String tags, Integer nbrOfLike,String timeLength,Producer producer,
                   Integer nbrOfShare, Integer nbrOfPLay,Integer nbrOfPurchase, Float basicPrice,Float exclusivePrice ) {
        super(title,timeLength, description, tags, nbrOfLike, producer,nbrOfShare, nbrOfPLay, nbrOfPurchase,basicPrice,exclusivePrice);
    }
}
