package com.keita.musicbay.model;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Data
public class Liking implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Customer customer;

    @OneToOne
    private Music music;
    private LocalDateTime likingDate;

    public Liking() { }

    @Builder
    public Liking(Customer customer,Music music) {
        this.customer = customer;
        this.music = music;
        this.likingDate = LocalDateTime.now();
    }
}
