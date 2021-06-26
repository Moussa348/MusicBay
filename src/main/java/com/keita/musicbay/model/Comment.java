package com.keita.musicbay.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Comment extends Text implements Serializable {

    private Integer nbrLike;

    @ManyToOne
    private Music music;

    public Comment() { }

    public Comment(Long id, String text, Integer nbrLike, Music music) {
        super(id, text);
        this.nbrLike = nbrLike;
        this.music = music;
    }
}
