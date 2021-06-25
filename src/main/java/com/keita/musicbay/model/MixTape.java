package com.keita.musicbay.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class MixTape extends MusicArticle implements Serializable {

    @OneToMany
    private List<Track> tracks;

    public MixTape(){}

    public MixTape(String title, String description, String tags, Integer nbrOfLike, Integer nbrOfShare,
                   Integer nbrOfPLay, Float price, List<Comment> comments) {
        super(title, description, tags, nbrOfLike, nbrOfShare, nbrOfPLay, price, comments);
    }
}
