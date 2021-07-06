package com.keita.musicbay.model;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@ToString
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Music implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID uuid;
    private String title,description,tags,timeLength;
    private LocalDateTime date;
    private Integer nbrOfLike,nbrOfShare,nbrOfPlay,nbrOfPurchase;
    private Float price;

    @Lob
    @Column(name ="picture",columnDefinition = "BLOB")
    private byte[] picture;
    private boolean hasAgreement;

    @ManyToOne
    private Producer producer;

    @OneToMany(mappedBy = "music",cascade = CascadeType.ALL)
    private List<Comment> comments;

    public Music() {}

    public Music(String title,String timeLength, String description, String tags, Integer nbrOfLike,
                 Integer nbrOfShare, Integer nbrOfPlay, Integer nbrOfPurchase,Float price) {
        this.uuid = UUID.randomUUID();
        this.title = title;
        this.timeLength = timeLength;
        this.description = description;
        this.tags = tags;
        this.date = LocalDateTime.now();
        this.nbrOfLike = nbrOfLike;
        this.nbrOfShare = nbrOfShare;
        this.nbrOfPlay = nbrOfPlay;
        this.nbrOfPurchase = nbrOfPurchase;
        this.price = price;
        this.comments = new ArrayList<>();
    }
}
