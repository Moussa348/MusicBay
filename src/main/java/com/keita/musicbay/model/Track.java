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
public class Track extends Music implements Serializable {

    @OneToMany
    private List<MediaFile> mediaFiles;
    private Float bpm;

    public Track(){}

    public Track(String title, String description, String tags, Integer nbrOfLike, Integer nbrOfShare,
                 Integer nbrOfPLay, Float price, List<Comment> comments, Float bpm) {
        super(title, description, tags, nbrOfLike, nbrOfShare, nbrOfPLay, price, comments);
        this.bpm = bpm;
    }
}
