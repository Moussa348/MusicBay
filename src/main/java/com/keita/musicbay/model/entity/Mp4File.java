package com.keita.musicbay.model.entity;

import javax.persistence.Entity;
import java.io.Serializable;

@Entity
public class Mp4File extends File implements Serializable {

    public Mp4File() { }

    public Mp4File(Long id, String fileName, byte[] data, Track track) {
        super(id, fileName, data, track);
    }
}