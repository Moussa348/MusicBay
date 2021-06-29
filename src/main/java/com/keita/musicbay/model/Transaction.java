package com.keita.musicbay.model;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
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
    private boolean paymentApproved;

    @ManyToOne
    private Customer customer;

    @OneToMany(mappedBy = "transaction")
    private List<Music> musics;

    @OneToMany(mappedBy = "transaction")
    private List<Contract> contracts;

    public Transaction(){}

    @Builder
    public Transaction(Float total, Customer customer,List<Music> musics) {
        this.uuid = UUID.randomUUID();
        this.date = LocalDateTime.now();
        this.total = total;
        this.musics = musics;
        this.customer = customer;
    }
}
