package com.keita.musicbay.model.entity;

import lombok.Builder;

import javax.persistence.Entity;
import java.io.Serializable;

@Entity
public class Mp3File extends File implements Serializable {

    public Mp3File() { }

    @Builder
    public Mp3File(Long id, String fileName, byte[] data, Track track) {
        super(id, fileName, data, track);
    }
}
