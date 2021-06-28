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

    @ManyToOne
    private Customer customer;

    @OneToMany(mappedBy = "transaction")
    private List<Agreement> agreements;

    public Transaction(){}

    @Builder
    public Transaction(Float total, Customer customer) {
        this.uuid = UUID.randomUUID();
        this.date = LocalDateTime.now();
        this.total = total;
        this.customer = customer;
    }
}
