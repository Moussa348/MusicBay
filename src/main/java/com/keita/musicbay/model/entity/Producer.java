package com.keita.musicbay.model.entity;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Producer extends User implements Serializable {

    @OneToMany(mappedBy = "producer")
    private List<Music> musics;

    public Producer() {
    }
    @Builder
    public Producer(String firstName, String lastName, byte[] picture, LocalDate dateOfBirth, String cellNumber, String city, String email, String username, String password, String biography, String roles) {
        super(firstName, lastName, picture, dateOfBirth, cellNumber, city, email, username, password, biography, roles);
    }
}
