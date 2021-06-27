package com.keita.musicbay.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class File implements Serializable {

    @Id
    @GeneratedValue
    private Long id;
    private String fileName;

    @Lob
    @Column(name ="data",columnDefinition = "BLOB")
    private byte[] data;

    @ManyToOne
    private Track track;

    public File(){}

    public File(Long id, String fileName, byte[] data, Track track) {
        this.id = id;
        this.fileName = fileName;
        this.data = data;
        this.track = track;
    }
}
