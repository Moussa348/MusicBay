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
    private MusicArticle musicArticle;

    public Comment() { }

    public Comment(Long id, String text, Integer nbrLike,MusicArticle musicArticle) {
        super(id, text);
        this.nbrLike = nbrLike;
        this.musicArticle = musicArticle;
    }
}
