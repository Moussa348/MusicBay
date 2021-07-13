package com.keita.musicbay.model.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Data
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Text implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private LocalDateTime date;

    @Lob
    @Column(name ="content",columnDefinition = "CLOB")
    private String content;
    private String sendBy;

    public Text(){}

    public Text(Long id,String content, String sendBy) {
        this.id = id;
        this.date = LocalDateTime.now();
        this.content = content;
        this.sendBy = sendBy;
    }
}
