package com.keita.musicbay.model.entity;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Contract extends File implements Serializable {

    @ManyToOne
    private Music music;
    private LocalDateTime date;
    private String type,description;

    public Contract(){}

    @Builder
    public Contract(Long id, String fileName, byte[] data, Track track) {
        super(id, fileName, data, track);
        this.date = LocalDateTime.now();
    }
}
