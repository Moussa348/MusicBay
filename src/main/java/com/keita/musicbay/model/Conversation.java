package com.keita.musicbay.model;

import com.keita.musicbay.model.enums.ConversationType;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
public class Conversation implements Serializable {

    @Id
    @GeneratedValue
    private Long id;
    private LocalDateTime creationDate;
    private String name;
    private ConversationType conversationType;

    @OneToMany
    private List<User> user;

    @OneToMany
    private List<Message> messages;


}
