package com.keita.musicbay.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Data
public class Purchasing implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Customer customer;

    @OneToOne
    private Music music;
    private LocalDateTime purchasingDate;

    public Purchasing() { }

    @Builder
    public Purchasing(Customer customer,Music music,LocalDateTime purchasingDate) {
        this.customer = customer;
        this.music = music;
        this.purchasingDate = purchasingDate;
    }
}