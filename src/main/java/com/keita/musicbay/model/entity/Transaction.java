package com.keita.musicbay.model.entity;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Data
public class Transaction implements Serializable {

    @Id
    @GeneratedValue
    private UUID uuid;
    private LocalDateTime date;
    private Float total;
    private boolean paymentApproved,confirmed,articleAreSent;

    @ManyToOne
    private Customer customer;

    @OneToMany
    private List<Music> musics;

    @OneToMany(mappedBy = "transaction",orphanRemoval = true,cascade = CascadeType.ALL)
    private List<Article> articles;

    public Transaction(){}

    @Builder
    public Transaction(Float total, Customer customer) {
        this.uuid = UUID.randomUUID();
        this.date = LocalDateTime.now();
        this.total = total;
        this.musics = new ArrayList<>();
        this.articles = new ArrayList<>();
        this.customer = customer;
        this.paymentApproved = false;
        this.confirmed = false;
        this.articleAreSent = false;
    }
}
