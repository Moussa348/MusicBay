package com.keita.musicbay.model;

import lombok.Builder;
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

    @Builder
    public Message(Long id,String sendBy, String content,List<User> users) {
        super(id,content,sendBy);
        this.users = users;
    }

    /*

    public Message(TextDTO textDTO,List<User> users){
        super(textDTO.getId(), textDTO.getContent(), textDTO.getSendBy());
        this.users = users;
    }
     */
}
