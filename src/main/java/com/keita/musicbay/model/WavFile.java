package com.keita.musicbay.model;

import javax.persistence.Entity;
import java.io.Serializable;

@Entity
public class WavFile extends MediaFile implements Serializable {

    public WavFile() { }

    public WavFile(Long id, String fileName, byte[] data, Track track) {
        super(id, fileName, data, track);
    }
}
