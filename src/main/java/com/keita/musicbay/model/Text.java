package com.keita.musicbay.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Text implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Lob
    @Column(name ="content",columnDefinition = "CLOB")
    private String content;
    private String sendBy;

    public Text(){}

    public Text(Long id,String content, String sendBy) {
        this.id = id;
        this.content = content;
        this.sendBy = sendBy;
    }
}
