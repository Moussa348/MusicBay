package com.keita.musicbay.model.dto;

import com.keita.musicbay.model.entity.Conversation;
import com.keita.musicbay.model.entity.User;
import com.keita.musicbay.model.enums.ConversationType;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class ConversationDTO implements Serializable {
    private Long id;
    private String name,createdBy;
    private LocalDateTime creationDate;
    private ConversationType conversationType;
    private List<String> usernames;
    private List<SentMessage> sentMessages;

    public ConversationDTO(){}

    public ConversationDTO(Conversation conversation) {
        this.id = conversation.getId();
        this.name = conversation.getName();
        this.createdBy = conversation.getCreatedBy();
        this.creationDate = conversation.getCreationDate();
        this.conversationType = conversation.getConversationType();
        this.usernames = conversation.getUsers().stream().map(User::getUsername).collect(Collectors.toList());
        this.sentMessages = conversation.getMessages().stream().map(SentMessage::new).collect(Collectors.toList());
    }

    public ConversationDTO(Conversation conversation,List<SentMessage> sentMessages) {
        this.id = conversation.getId();
        this.name = conversation.getName();
        this.createdBy = conversation.getCreatedBy();
        this.creationDate = conversation.getCreationDate();
        this.conversationType = conversation.getConversationType();
        this.usernames = conversation.getUsers().stream().map(User::getUsername).collect(Collectors.toList());
        this.sentMessages = sentMessages;
    }

    public ConversationDTO(String name,ConversationType conversationType){
        this.name=name;
        this.conversationType = conversationType;
        this.usernames = new ArrayList<>();
        this.sentMessages = new ArrayList<>();
        this.creationDate = LocalDateTime.now();
    }

    @Builder
    public ConversationDTO(String name,String createdBy, ConversationType conversationType, List<String> usernames) {
        this.name = name;
        this.createdBy = createdBy;
        this.creationDate = LocalDateTime.now();
        this.conversationType = conversationType;
        this.usernames = usernames;
    }
}
