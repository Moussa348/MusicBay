package com.keita.musicbay.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Text implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @Lob
    @Column(name ="text",columnDefinition = "CLOB")
    private String text;

    public Text(){}

    public Text(Long id, String text) {
        this.id = id;
        this.text = text;
    }
}
