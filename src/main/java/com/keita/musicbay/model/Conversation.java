package com.keita.musicbay.model;

import com.keita.musicbay.model.dto.ConversationDTO;
import com.keita.musicbay.model.enums.ConversationType;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
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
    private boolean active;

    @ManyToMany(mappedBy = "conversations")
    private List<User> users = new ArrayList<>();

    @OneToMany(mappedBy = "conversation",cascade = CascadeType.ALL)
    private List<Message> messages = new ArrayList<>();

    public Conversation(){}

    @Builder
    public Conversation(Long id,String createdBy, LocalDateTime creationDate, String name, ConversationType conversationType) {
        this.id = id;
        this.creationDate = creationDate;
        this.createdBy = createdBy;
        this.name = name;
        this.conversationType = conversationType;
        this.active = true;
    }

    public Conversation(ConversationDTO conversationDTO,List<User> users){
        this.creationDate = LocalDateTime.now();
        this.name = conversationDTO.getName();
        this.createdBy = conversationDTO.getCreatedBy();
        this.conversationType = conversationDTO.getConversationType();
        this.users = users;
        this.active = true;
    }
}
