package com.keita.musicbay.model.entity;

import com.keita.musicbay.model.dto.SentMessage;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Message extends Text implements Serializable {

    @ManyToOne
    private Conversation conversation;

    public Message(){}

    @Builder
    public Message(Long id, String content, String sendBy,Conversation conversation) {
        super(id, content, sendBy);
        super.setDate(LocalDateTime.now());
        this.conversation = conversation;
    }

    public Message (SentMessage sentMessage, Conversation conversation){
        super(sentMessage.getId(),sentMessage.getContent(),sentMessage.getSendBy());
        super.setDate(LocalDateTime.now());
        this.conversation = conversation;
    }
}
