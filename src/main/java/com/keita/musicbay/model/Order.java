package com.keita.musicbay.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
public class Order implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID uuid;
    private LocalDateTime date;
    private Float total;

    @ManyToOne
    private Customer customer;

    @OneToOne
    private Agreement agreement;

    public Order(){}

    public Order(Float total, Customer customer) {
        this.uuid = UUID.randomUUID();
        this.date = LocalDateTime.now();
        this.total = total;
        this.customer = customer;
    }
}
