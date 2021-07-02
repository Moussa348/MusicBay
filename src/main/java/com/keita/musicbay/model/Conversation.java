package com.keita.musicbay.model;

import com.keita.musicbay.model.enums.ConversationType;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Conversation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime creationDate;
    private String name,createdBy;
    private ConversationType conversationType;

    @OneToMany
    private List<User> user = new ArrayList<>();

    @OneToMany(mappedBy = "conversation",cascade = CascadeType.ALL)
    private List<Message> messages = new ArrayList<>();

    public Conversation(){}

    @Builder
    public Conversation(Long id, LocalDateTime creationDate, String name, ConversationType conversationType) {
        this.id = id;
        this.creationDate = creationDate;
        this.name = name;
        this.conversationType = conversationType;
    }
}
