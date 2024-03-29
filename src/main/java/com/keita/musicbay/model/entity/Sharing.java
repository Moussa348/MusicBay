package com.keita.musicbay.model.entity;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Data
public class Sharing implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Customer customer;

    @OneToOne
    private Music music;
    private LocalDateTime sharingDate;
    private String sharingMsg;

    public Sharing() { }

    @Builder
    public Sharing( Customer customer,Music music, String sharingMsg) {
        this.customer = customer;
        this.music = music;
        this.sharingDate = LocalDateTime.now();
        this.sharingMsg = sharingMsg;
    }
}
