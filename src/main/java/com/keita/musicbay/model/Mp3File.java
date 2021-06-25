package com.keita.musicbay.model;

import javax.persistence.Entity;
import java.io.Serializable;

@Entity
public class Mp3File extends MediaFile implements Serializable {

    public Mp3File() { }

    public Mp3File(Long id, String fileName, byte[] data, Track track) {
        super(id, fileName, data, track);
    }
}
