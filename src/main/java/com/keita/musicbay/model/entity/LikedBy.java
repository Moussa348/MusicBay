package com.keita.musicbay.model.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Entity
@Data
public class LikedBy implements Serializable {

    @Id
    @GeneratedValue
    private Long id;
    private String username;

    @ManyToOne
    private Comment comment;

    public LikedBy(){}

    public LikedBy(String username, Comment comment) {
        this.username = username;
        this.comment = comment;
    }
}
