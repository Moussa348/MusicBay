package com.keita.musicbay.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.io.Serializable;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Message extends Text implements Serializable {

    @ManyToMany
    private List<User> users;

    public Message(){}

    public Message(Long id, String text,List<User> users) {
        super(id, text);
        this.users = users;
    }
}
